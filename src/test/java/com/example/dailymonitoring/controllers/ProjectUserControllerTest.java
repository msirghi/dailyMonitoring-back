package com.example.dailymonitoring.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.dailymonitoring.models.ProjectUserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.HashSet;
import javax.validation.ConstraintViolationException;
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

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectUserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  public ProjectUserData createModel() {
    return ProjectUserData
        .builder()
        .projectId(1L)
        .userEmail("user1@email.com")
        .build();
  }

  public String generateJson(ProjectUserData projectUserData) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(projectUserData);
  }

  @Test
  @Order(1)
  public void addUserToTheProject() throws Exception {
    ProjectUserData projectUserData = createModel();
    projectUserData.setUserEmail("user2@email.com");
    String json = generateJson(projectUserData);

    mockMvc.perform(post("/projects/addUser")
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(2)
  public void getListOfProjectUsers() throws Exception {
    mockMvc.perform(get("/users/1/projects/1/users")
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$.[0].fullName").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.[0].email").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.[1].fullName").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.[1].email").value(IsNull.notNullValue()))
        .andExpect(status().isOk());
  }

  @Test
  @Order(3)
  public void addAlreadyAddedUserToTheProject() throws Exception {
    ProjectUserData projectUserData = createModel();
    projectUserData.setUserEmail("user2@email.com");
    String json = generateJson(projectUserData);

    mockMvc.perform(post("/projects/addUser")
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(4)
  public void addUserToTheProjectWithoutEmail() throws Exception {
    ProjectUserData projectUserData = createModel();
    projectUserData.setUserEmail(null);
    String json = generateJson(projectUserData);

    mockMvc.perform(post("/projects/addUser")
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(5)
  public void addUserToTheProjectWithoutProjectId() throws Exception {
    ProjectUserData projectUserData = createModel();
    projectUserData.setProjectId(null);
    String json = generateJson(projectUserData);

    mockMvc.perform(post("/projects/addUser")
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(6)
  public void addNonExistingUserToTheProject() throws Exception {
    ProjectUserData projectUserData = createModel();
    projectUserData.setUserEmail("non-existing@mail.com");
    String json = generateJson(projectUserData);

    mockMvc.perform(post("/projects/addUser")
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(7)
  public void addUserToTheNonExistingProject() throws Exception {
    ProjectUserData projectUserData = createModel();
    projectUserData.setProjectId(99L);
    String json = generateJson(projectUserData);

    mockMvc.perform(post("/projects/addUser")
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(8)
  public void addNonExistingUserToTheNonExistingProject() throws Exception {
    ProjectUserData projectUserData = createModel();
    projectUserData.setProjectId(99L);
    projectUserData.setUserEmail("non-existing@mail.com");
    String json = generateJson(projectUserData);

    mockMvc.perform(post("/projects/addUser")
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(9)
  public void deleteUserFromAnExistingProject() throws Exception {
    mockMvc.perform(delete("/projects/{projectId}/users/{userId}", 1, 6)
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(204));
  }

  @Test
  @Order(10)
  public void getListOfProjectUsersAfterDeletion() throws Exception {
    mockMvc.perform(get("/users/1/projects/1/users")
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$.[0].fullName").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.[0].email").value(IsNull.notNullValue()))
        .andExpect(status().isOk());
  }

  @Test
  @Order(11)
  public void deleteAlreadyDeletedUserFromAnExistingProject() throws Exception {
    mockMvc.perform(delete("/projects/{projectId}/users/{userId}", 1, 6)
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(12)
  public void deleteNonExistingUserFromAnExistingProject() throws Exception {
    mockMvc.perform(delete("/projects/{projectId}/users/{userId}", 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(13)
  public void deleteNonRelatedUserFromAnExistingProject() throws Exception {
    mockMvc.perform(delete("/projects/{projectId}/users/{userId}", 2, 2)
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(14)
  public void deleteUserFromNonExistingProject() throws Exception {
    mockMvc.perform(delete("/projects/{projectId}/users/{userId}", 99, 1)
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(15)
  public void deleteNonExistingUserFromProject() throws Exception {
    mockMvc.perform(delete("/projects/{projectId}/users/{userId}", 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(16)
  public void deleteNonExistingUserFromNonExistingProject() throws Exception {
    mockMvc.perform(delete("/projects/{projectId}/users/{userId}", 99, 99)
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(17)
  public void deleteUserWithNegativeIdFromNonExistingProject() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(delete("/projects/{projectId}/users/{userId}", 2, -1)
            .accept(MediaType.APPLICATION_JSON)
            .header("Origin", "*")
            .header("Content-type", "application/json")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError()))
        .hasCause(new ConstraintViolationException(
            "deleteUserFromProject.userId: must be greater than or equal to 1", new HashSet<>()));
  }

  @Test
  @Order(18)
  public void deleteUserFromNonExistingProjectWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(delete("/projects/{projectId}/users/{userId}", -1, 2)
            .accept(MediaType.APPLICATION_JSON)
            .header("Origin", "*")
            .header("Content-type", "application/json")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError()))
        .hasCause(new ConstraintViolationException(
            "deleteUserFromProject.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(19)
  public void getUserWithNegativeIdFromExistingProject() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get("/users/{userId}/projects/{projectId}/users", -1, 2)
            .accept(MediaType.APPLICATION_JSON)
            .header("Origin", "*")
            .header("Content-type", "application/json")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError()))
        .hasCause(new ConstraintViolationException(
            "getAllProjectUsers.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(20)
  public void getUserFromExistingProjectWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get("/users/{userId}/projects/{projectId}/users", 1, -1)
            .accept(MediaType.APPLICATION_JSON)
            .header("Origin", "*")
            .header("Content-type", "application/json")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError()))
        .hasCause(new ConstraintViolationException(
            "getAllProjectUsers.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(21)
  public void createProjectUserWithoutBody() throws Exception {
    mockMvc.perform(post("/projects/addUser")
        .accept(MediaType.APPLICATION_JSON)
        .header("Origin", "*")
        .header("Content-type", "application/json")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}