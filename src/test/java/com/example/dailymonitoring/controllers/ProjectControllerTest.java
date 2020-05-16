package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.models.ProjectData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    mockMvc.perform(post(baseUrl, 1)
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
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(status().isOk());
  }
}
