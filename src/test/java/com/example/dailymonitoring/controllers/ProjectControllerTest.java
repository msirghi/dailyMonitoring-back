package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.models.ProjectData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.Assertions;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private final String baseUrl = "/users/{userId}/projects";

  public ProjectData createProjectData() {
    return ProjectData
        .builder()
        .name("Project 1")
        .description("Description")
        .build();
  }

  public String generateJson(ProjectData projectData) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(projectData);
  }

  @Test
  @Order(1)
  public void projectCreate() throws Exception {
    String json = generateJson(createProjectData());

    mockMvc.perform(post(baseUrl, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.name").value("Project 1"))
        .andExpect(jsonPath("$.description").value("Description"))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(2)
  public void projectGetById() throws Exception {
    mockMvc.perform(get(baseUrl + "/{projectId}", 1, 5)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.name").value("Project 1"))
        .andExpect(jsonPath("$.description").value("Description"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(3)
  public void projectGetNotFound() throws Exception {
    mockMvc.perform(get(baseUrl + "/{projectId}", 1, 122)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(4)
  public void projectCreateWithoutName() throws Exception {
    ProjectData projectData = createProjectData();
    projectData.setName(null);
    String json = generateJson(projectData);
    mockMvc.perform(post(baseUrl, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(5)
  public void projectCreateWithoutDescription() throws Exception {
    ProjectData projectData = createProjectData();
    projectData.setDescription(null);
    String json = generateJson(projectData);
    mockMvc.perform(post(baseUrl, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(6)
  public void getUserProjects() throws Exception {
    ProjectData projectData = createProjectData();
    String json = generateJson(projectData);

    mockMvc.perform(get(baseUrl, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(jsonPath("$.[0].id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.[0].name").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.[0].description").exists())
        .andExpect(status().isOk());
  }

  @Test
  @Order(7)
  public void createProjectForNonExistingUser() throws Exception {
    ProjectData projectData = createProjectData();
    String json = generateJson(projectData);

    mockMvc.perform(post(baseUrl, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(8)
  public void updateExistingProject() throws Exception {
    ProjectData projectData = createProjectData();
    projectData.setDescription("New description");
    String json = generateJson(projectData);

    mockMvc.perform(put(baseUrl + "/{projectId}", 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.name").value(projectData.getName()))
        .andExpect(jsonPath("$.description").value("New description"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(9)
  public void updateNonExistingProject() throws Exception {
    ProjectData projectData = createProjectData();
    String json = generateJson(projectData);

    mockMvc.perform(put(baseUrl + "/{projectId}", 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(10)
  public void updateOnlyProjectName() throws Exception {
    ProjectData projectData = createProjectData();
    projectData.setName("New name");
    String json = generateJson(projectData);

    mockMvc.perform(put(baseUrl + "/{projectId}", 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.name").value("New name"))
        .andExpect(jsonPath("$.description").exists())
        .andExpect(status().isOk());
  }

  @Test
  @Order(11)
  public void deleteNonExistingProject() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{projectId}", 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(12)
  public void deleteProjectById() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{projectId}", 1, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(13)
  public void deleteAlreadyDeletedProjectById() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{projectId}", 1, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(14)
  public void getAlreadyDeletedProjectById() throws Exception {
    mockMvc.perform(get(baseUrl + "/{projectId}", 1, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(15)
  public void getUserProjectsWithZeroSize() throws Exception {
    mockMvc.perform(get(baseUrl, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(status().isOk());
  }

  @Test
  @Order(16)
  public void getUserProjectsAfterDeleting() throws Exception {
    mockMvc.perform(get(baseUrl, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(status().isOk());
  }

  @Test
  @Order(17)
  public void changeProjectName() throws Exception {
    ProjectData model = createProjectData();
    model.setName("New name");
    String json = generateJson(model);
    mockMvc.perform(put(baseUrl + "/{projectId}/name", 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk());
  }

  @Test
  @Order(18)
  public void getProjectAfterNameChange() throws Exception {
    mockMvc.perform(get(baseUrl + "/{projectId}", 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value("New name"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(19)
  public void createProjectForUserWithNegativeId() throws Exception {
    String json = generateJson(createProjectData());

    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(post(baseUrl, -1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)))
        .hasCause(new ConstraintViolationException(
            "projectCreate.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(20)
  public void getProjectForUserWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/{projectId}", -1, 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "getProjectById.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(21)
  public void getProjectWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/{projectId}", 1, -1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "getProjectById.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(22)
  public void getListOfProjectsForUserWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl, -1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "getProjectsByUser.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(23)
  public void deleteProjectForUserWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(delete(baseUrl + "/{projectId}", -1, 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "projectDelete.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(24)
  public void deleteProjectForUserWithNegativeProjectId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(delete(baseUrl + "/{projectId}", 1, -1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "projectDelete.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(25)
  public void updateProjectForUserWithNegativeProjectId() throws Exception {
    mockMvc.perform(put(baseUrl + "/{projectId}", 1, -1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @Order(26)
  public void updateProjectForUserWithNegativeId() throws Exception {
    mockMvc.perform(put(baseUrl + "/{projectId}", -1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @Order(27)
  public void changeNameForProjectWithNegativeUserId() throws Exception {
    mockMvc.perform(put(baseUrl + "/{projectId}/name", -1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @Order(28)
  public void changeNameForProjectWithNegativeProjectId() throws Exception {
    mockMvc.perform(put(baseUrl + "/{projectId}/name", 1, -1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @Order(29)
  public void getProjectStatistics() throws Exception {
    mockMvc.perform(get(baseUrl + "/{projectId}/statistics", 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.pieStatistics").isArray())
        .andExpect(jsonPath("$.pieStatistics[0].userId").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.pieStatistics[0].fullName").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.pieStatistics[0].tasksDone").isNumber())
        .andExpect(jsonPath("$.pieStatistics[0].taskPercentage").isNumber())
        .andExpect(jsonPath("$.projectTaskCount").isNumber())
        .andExpect(jsonPath("$.doneProjectTaskCount").isNumber())
        .andExpect(jsonPath("$.undoneProjectTaskCount").isNumber())
        .andExpect(status().isOk());
  }

  @Test
  @Order(30)
  public void getProjectTasksStatisticsForNonExistingUser() throws Exception {
    mockMvc.perform(get(baseUrl + "/{projectId}/statistics", 99, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(31)
  public void getProjectTasksStatisticsForNonExistingProject() throws Exception {
    mockMvc.perform(get(baseUrl + "/{projectId}/statistics", 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(32)
  public void getProjectTasksStatisticsForUserWithNegativeId() throws Exception {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/{projectId}/statistics", -1, 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "getTasksStatistics.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(33)
  public void getProjectTasksStatisticsForProjectWithNegativeId() throws Exception {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/{projectId}/statistics", 1, -1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "getTasksStatistics.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(34)
  public void getProjectStatisticsForDeletedProject() throws Exception {
    mockMvc.perform(get(baseUrl + "/{projectId}/statistics", 1, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(35)
  public void getProjectStatisticsForUserWithNonRelatedProject() throws Exception {
    mockMvc.perform(get(baseUrl + "/{projectId}/statistics", 1, 4)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(36)
  public void getProjectsWithOrderNumber() throws Exception {
    mockMvc.perform(get(baseUrl, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.[0].orderNumber").value(1))
        .andExpect(jsonPath("$.[0].id").value(1))
        .andExpect(jsonPath("$.[1].orderNumber").value(2))
        .andExpect(jsonPath("$.[1].id").value(3))
        .andExpect(status().isOk());
  }

  @Test
  @Order(37)
  public void switchProjectNumbers() throws Exception {
    mockMvc.perform(patch(baseUrl + "/reorder?firstProject=1&secondProject=3", 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @Order(38)
  public void getProjectsAfterOrderChange() throws Exception {
    mockMvc.perform(get(baseUrl, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.[0].orderNumber").value(1))
        .andExpect(jsonPath("$.[0].id").value(3))
        .andExpect(jsonPath("$.[1].orderNumber").value(2))
        .andExpect(jsonPath("$.[1].id").value(1))
        .andExpect(status().isOk());
  }

  @Test
  @Order(39)
  public void switchProjectsForNonExistingUser() throws Exception {
    mockMvc.perform(patch(baseUrl + "/reorder?firstProject=1&secondProject=3", 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(40)
  public void switchProjectsForNonFirstProject() throws Exception {
    mockMvc.perform(patch(baseUrl + "/reorder?firstProject=99&secondProject=3", 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(41)
  public void switchProjectsForNonExistingSecondProject() throws Exception {
    mockMvc.perform(patch(baseUrl + "/reorder?firstProject=1&secondProject=99", 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(42)
  public void switchProjectsWithNegativeIdForFirstProject() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(patch(baseUrl + "/reorder?firstProject=-1&secondProject=1", 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest()))
        .hasCause(new ConstraintViolationException(
            "changeProjectOrder.firstProject: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(43)
  public void switchProjectsWithNegativeIdForSecondProject() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(patch(baseUrl + "/reorder?firstProject=1&secondProject=-1", 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest()))
        .hasCause(new ConstraintViolationException(
            "changeProjectOrder.secondProject: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(44)
  public void switchProjectsWithNegativeUserId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(patch(baseUrl + "/reorder?firstProject=1&secondProject=1", -1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest()))
        .hasCause(new ConstraintViolationException(
            "changeProjectOrder.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(45)
  public void deleteProjectBeforeSwitching() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{projectId}", 1, 3)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(46)
  public void switchProjectsWithOneOfThemDeleted() throws Exception {
    mockMvc.perform(patch(baseUrl + "/reorder?firstProject=1&secondProject=3", 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(47)
  public void changeProjectColor() throws Exception {
    ProjectData model = createProjectData();
    model.setColor("#fff");
    String json = generateJson(model);
    mockMvc.perform(patch(baseUrl + "/{projectId}/color", 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(jsonPath("$.color").value("#fff"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(48)
  public void changeProjectColorForNonExistingUser() throws Exception {
    ProjectData model = createProjectData();
    model.setColor("#fff");
    String json = generateJson(model);
    mockMvc.perform(patch(baseUrl + "/{projectId}/color", 99, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(49)
  public void changeProjectColorForNonExistingProject() throws Exception {
    ProjectData model = createProjectData();
    model.setColor("#fff");
    String json = generateJson(model);
    mockMvc.perform(patch(baseUrl + "/{projectId}/color", 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(50)
  public void changeProjectColorForUserWithNegativeId() throws Exception {
    ProjectData model = createProjectData();
    model.setColor("#fff");
    String json = generateJson(model);
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(patch(baseUrl + "/{projectId}/color", -1, 4)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)))
        .hasCause(new ConstraintViolationException(
            "updateProjectColor.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(51)
  public void changeProjectColorForProjectWithNegativeId() throws Exception {
    ProjectData model = createProjectData();
    model.setColor("#fff");
    String json = generateJson(model);
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(patch(baseUrl + "/{projectId}/color", 1, -1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)))
        .hasCause(new ConstraintViolationException(
            "updateProjectColor.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(52)
  public void changeProjectColorWithNullNameInDTO() throws Exception {
    ProjectData model = createProjectData();
    model.setName(null);
    model.setColor("#fff");
    String json = generateJson(model);
    mockMvc.perform(patch(baseUrl + "/{projectId}/color", 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }
}
