package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.models.ProjectAlertData;
import com.example.dailymonitoring.models.enums.AlertType;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sirghi Mihail
 */
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class _zProjectAlertControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private final String baseUrl = "/users/{userId}/projects/{projectId}/alerts";

  public ProjectAlertData getModel() {
    return ProjectAlertData
        .builder()
        .type(AlertType.INFO)
        .message("Alert message")
        .areMembersNotified(false)
        .build();
  }

  public String generateJson(ProjectAlertData data) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(data);
  }

  @Test
  @Order(1)
  public void createProjectAlert() throws Exception {
    ProjectAlertData model = getModel();
    String json = generateJson(model);

    mockMvc.perform(post(baseUrl, 3, 1)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.message").value(model.getMessage()))
        .andExpect(jsonPath("$.authorId").value(3))
        .andExpect(jsonPath("$.authorName").value(IsNull.notNullValue()))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(2)
  public void createProjectAlertWithoutMessage() throws Exception {
    ProjectAlertData model = getModel();
    model.setMessage(null);
    String json = generateJson(model);

    mockMvc.perform(post(baseUrl, 3, 1)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(3)
  public void createProjectAlertWithoutType() throws Exception {
    ProjectAlertData model = getModel();
    model.setType(null);
    String json = generateJson(model);

    mockMvc.perform(post(baseUrl, 3, 1)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(4)
  public void createProjectAlertForNonExistingRelationBetweenUserAndProject() throws Exception {
    ProjectAlertData model = getModel();
    model.setType(null);
    String json = generateJson(model);

    mockMvc.perform(post(baseUrl, 2, 3)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(5)
  public void createProjectAlertForNonExistingUser() throws Exception {
    ProjectAlertData model = getModel();
    String json = generateJson(model);

    mockMvc.perform(post(baseUrl, 99, 3)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(6)
  public void createProjectAlertForNonExistingProject() throws Exception {
    ProjectAlertData model = getModel();
    String json = generateJson(model);

    mockMvc.perform(post(baseUrl, 1, 99)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(7)
  public void createProjectAlertForUserWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(post(baseUrl, -1, 99)
            .content(generateJson(getModel()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()))
        .hasCause(new ConstraintViolationException(
            "addProjectAlert.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(8)
  public void createProjectAlertForProjectWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(post(baseUrl, 1, -1)
            .content(generateJson(getModel()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()))
        .hasCause(new ConstraintViolationException(
            "addProjectAlert.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(9)
  public void getProjectAlertWithNegativeUserId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/{alertId}", -1, 1, 1)
            .content(generateJson(getModel()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()))
        .hasCause(new ConstraintViolationException(
            "getProjectAlertById.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(10)
  public void getProjectAlertWithNegativeProjectId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/{alertId}", 1, -1, 1)
            .content(generateJson(getModel()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()))
        .hasCause(new ConstraintViolationException(
            "getProjectAlertById.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(11)
  public void getProjectAlertWithNegativeAlertId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/{alertId}", 1, 1, -1)
            .content(generateJson(getModel()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()))
        .hasCause(new ConstraintViolationException(
            "getProjectAlertById.alertId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(12)
  public void getProjectAlertById() throws Exception {
    mockMvc.perform(get(baseUrl + "/{alertId}", 3, 1, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.message").value("Alert message"))
        .andExpect(jsonPath("$.authorId").value(3))
        .andExpect(jsonPath("$.authorName").value(IsNull.notNullValue()))
        .andExpect(status().isOk());
  }

  @Test
  @Order(13)
  public void getNonExistingProjectAlertById() throws Exception {
    mockMvc.perform(get(baseUrl + "/{alertId}", 1, 1, 99)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(14)
  public void getListExistingProjectAlertById() throws Exception {
    mockMvc.perform(get(baseUrl, 3, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.[0].id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.[0].message").value("Alert message"))
        .andExpect(jsonPath("$.[0].authorId").value(3))
        .andExpect(jsonPath("$.[0].authorName").value(IsNull.notNullValue()))
        .andExpect(status().isOk());
  }

  @Test
  @Order(15)
  public void getAllProjectAlertsWithNegativeUserId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl, -1, 1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "getAllProjectAlerts.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(16)
  public void getAllProjectAlertsWithNegativeProjectId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl, 1, -1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "getAllProjectAlerts.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(17)
  public void getAllProjectAlertsForNonExistingProject() throws Exception {
    mockMvc.perform(get(baseUrl, 1, 99)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(18)
  public void getAllProjectAlertsForNonExistingUser() throws Exception {
    mockMvc.perform(get(baseUrl, 99, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(19)
  public void getAllProjectAlertsForNonRelatedUserAndProject() throws Exception {
    mockMvc.perform(get(baseUrl, 2, 3)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(20)
  public void deleteExistingProjectAlertById() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{alertId}", 3, 1, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(21)
  public void deleteAlreadyDeletedProjectAlertById() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{alertId}", 3, 1, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(22)
  public void getDeletedProjectAlertById() throws Exception {
    mockMvc.perform(get(baseUrl + "/{alertId}", 3, 1, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(23)
  public void deleteProjectAlertWithNegativeUserId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(delete(baseUrl + "/{alertId}", -1, 1, 1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "deleteProjectAlert.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(24)
  public void deleteProjectAlertWithNegativeProjectId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(delete(baseUrl + "/{alertId}", 1, -1, 1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "deleteProjectAlert.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(25)
  public void deleteProjectAlertWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(delete(baseUrl + "/{alertId}", 1, 1, -1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "deleteProjectAlert.alertId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(26)
  public void createProjectAlertBeforeUpdating() throws Exception {
    ProjectAlertData model = getModel();
    String json = generateJson(model);

    mockMvc.perform(post(baseUrl, 3, 1)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.message").value(model.getMessage()))
        .andExpect(jsonPath("$.authorId").value(3))
        .andExpect(jsonPath("$.authorName").value(IsNull.notNullValue()))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(27)
  public void updateProjectAlertMessageById() throws Exception {
    ProjectAlertData model = getModel();
    model.setMessage("Updated message");
    String json = generateJson(model);

    mockMvc.perform(patch(baseUrl + "/{alertId}", 3, 1, 2)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.message").value(model.getMessage()))
        .andExpect(jsonPath("$.authorId").value(3))
        .andExpect(jsonPath("$.authorName").value(IsNull.notNullValue()))
        .andExpect(status().isOk());
  }

  @Test
  @Order(28)
  public void updateMessageForNonExistingProjectAlertById() throws Exception {
    ProjectAlertData model = getModel();
    model.setMessage("Updated message");
    String json = generateJson(model);

    mockMvc.perform(patch(baseUrl + "/{alertId}", 3, 1, 9)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(29)
  public void updateAlertMessageForNegativeUserId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(patch(baseUrl + "/{alertId}", -1, 1, 9)
            .content(generateJson(getModel()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()))
        .hasCause(new ConstraintViolationException(
            "updateProjectAlertMessage.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(30)
  public void updateAlertMessageForNegativeProjectId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(patch(baseUrl + "/{alertId}", 1, -1, 9)
            .content(generateJson(getModel()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()))
        .hasCause(new ConstraintViolationException(
            "updateProjectAlertMessage.projectId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(31)
  public void updateAlertMessageForNegativeAlertId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(patch(baseUrl + "/{alertId}", 1, 1, -9)
            .content(generateJson(getModel()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()))
        .hasCause(new ConstraintViolationException(
            "updateProjectAlertMessage.alertId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(32)
  public void createProjectAlertForDeletedUser() throws Exception {
    mockMvc.perform(post(baseUrl, 1, 1)
        .content(generateJson(getModel()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(33)
  public void deleteProjectBeforeAlertCreate() throws Exception {
    mockMvc.perform(delete("/users/{userId}/projects/{projectId}", 3, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(34)
  public void createProjectAlertForDeletedProject() throws Exception {
    mockMvc.perform(post(baseUrl, 3, 1)
        .content(generateJson(getModel()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(35)
  public void updateProjectAlertMessageForDeletedProject() throws Exception {
    mockMvc.perform(patch(baseUrl + "/{alertId}", 3, 1, 1)
        .content(generateJson(getModel()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(36)
  public void updateProjectAlertMessageForDeletedUser() throws Exception {
    mockMvc.perform(patch(baseUrl + "/{alertId}", 1, 1, 1)
        .content(generateJson(getModel()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
