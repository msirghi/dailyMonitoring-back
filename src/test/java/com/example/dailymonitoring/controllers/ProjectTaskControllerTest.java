package com.example.dailymonitoring.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.models.TaskData;
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

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectTaskControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private final String baseUrl = "/users/{userId}/projects/{projectId}/tasks";

  public TaskData createTaskData() {
    return TaskData
        .builder()
        .name("Task 1")
        .description("Description 1")
        .categoryId(1L)
        .build();
  }

  public String generateJson(TaskData taskData) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(taskData);
  }

  @Test
  @Order(1)
  public void createProjectTaskForNonExistingUserProject() throws Exception {
    String json = generateJson(createTaskData());

    mockMvc.perform(post(baseUrl, 1, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(jsonPath("$.code").value(400))
        .andExpect(jsonPath("$.message").value(Constants.NO_USER_WITH_SUCH_PROJECT))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(2)
  public void createProjectTask() throws Exception {
    String json = generateJson(createTaskData());

    mockMvc.perform(post(baseUrl, 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.name").value("Task 1"))
        .andExpect(jsonPath("$.description").value("Description 1"))
        .andExpect(jsonPath("$.dates").value(IsNull.nullValue()))
        .andExpect(jsonPath("$.categoryId").exists())
        .andExpect(jsonPath("$.status").value("INPROGRESS"))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(3)
  public void createProjectTaskForNonExistingUser() throws Exception {
    String json = generateJson(createTaskData());

    mockMvc.perform(post(baseUrl, 99, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(jsonPath("$.code").value(400))
        .andExpect(jsonPath("$.message").value(Constants.NO_USER_WITH_SUCH_PROJECT))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(4)
  public void createProjectTaskForNonExistingProject() throws Exception {
    String json = generateJson(createTaskData());

    mockMvc.perform(post(baseUrl, 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(jsonPath("$.code").value(400))
        .andExpect(jsonPath("$.message").value(Constants.NO_USER_WITH_SUCH_PROJECT))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(5)
  public void createProjectTaskForDeleteProject() throws Exception {
    String json = generateJson(createTaskData());

    mockMvc.perform(post(baseUrl, 1, 4)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(6)
  public void getProjectTaskById() throws Exception {
    mockMvc.perform(get(baseUrl + "/{taskId}", 1, 1, 7)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.name").value("Task 1"))
        .andExpect(jsonPath("$.description").exists())
        .andExpect(jsonPath("$.dates").value(IsNull.nullValue()))
        .andExpect(jsonPath("$.status").value("INPROGRESS"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(7)
  public void getNonExistingProjectTaskById() throws Exception {
    mockMvc.perform(get(baseUrl + "/{taskId}", 1, 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(8)
  public void deleteNonExistingProjectTaskById() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{taskId}", 1, 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(9)
  public void deleteExistingProjectTaskById() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{taskId}", 1, 1, 7)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(10)
  public void getDeletedProjectTaskById() throws Exception {
    mockMvc.perform(get(baseUrl + "/{taskId}", 1, 1, 7)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(11)
  public void markProjectTaskAsDone() throws Exception {
    String json = generateJson(createTaskData());

    mockMvc.perform(post(baseUrl, 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated());

    mockMvc.perform(put(baseUrl + "/{taskId}/complete", 1, 1, 8)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @Order(12)
  public void markNonExistingProjectTaskAsDone() throws Exception {
    mockMvc.perform(put(baseUrl + "/{taskId}/complete", 1, 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(13)
  public void getAllProjectTasks() throws Exception {
    mockMvc.perform(get(baseUrl, 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(status().isOk());
  }

  @Test
  @Order(14)
  public void getLastDoneProjectTasks() throws Exception {
    mockMvc.perform(get(baseUrl + "/lastDone", 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(5)))
        .andExpect(jsonPath("$.[0].id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.[0].name").value(IsNull.notNullValue()))
        .andExpect(status().isOk());
  }

  @Test
  @Order(15)
  public void getLastDoneProjectTasksForNonExistingProject() throws Exception {
    mockMvc.perform(get(baseUrl + "/lastDone", 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }


  @Test
  @Order(16)
  public void getLastDoneProjectTasksForNonExistingUser() throws Exception {
    mockMvc.perform(get(baseUrl + "/lastDone", 99, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(17)
  public void getInProgressProjectTasks() throws Exception {
    mockMvc.perform(get(baseUrl + "/inprogress", 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(status().isOk());
  }

  @Test
  @Order(18)
  public void getInProgressProjectTasksForNonExistingProject() throws Exception {
    mockMvc.perform(get(baseUrl + "/inprogress", 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(19)
  public void getInProgressProjectTasksForNonExistingUser() throws Exception {
    mockMvc.perform(get(baseUrl + "/inprogress", 99, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(20)
  public void markProjectTaskAsDoneForNonExistingProject() throws Exception {
    mockMvc.perform(put(baseUrl + "/{taskId}/complete", 1, 99, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(21)
  public void markProjectTaskAsDoneForNonExistingUser() throws Exception {
    mockMvc.perform(put(baseUrl + "/{taskId}/complete", 99, 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(22)
  public void markProjectTaskAsDoneForNonExistingUserAndProject() throws Exception {
    mockMvc.perform(put(baseUrl + "/{taskId}/complete", 99, 99, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(23)
  public void markProjectTaskAsDoneForNonExistingRelationUserAndProject() throws Exception {
    mockMvc.perform(put(baseUrl + "/{taskId}/complete", 2, 1, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(24)
  public void markProjectTaskAsDoneForNonExistingRelationProjectAndUser() throws Exception {
    mockMvc.perform(put(baseUrl + "/{taskId}/complete", 1, 2, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(25)
  public void getLastDoneProjectTasksForNonExistingProjectAndUser() throws Exception {
    mockMvc.perform(get(baseUrl + "/lastDone", 99, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(26)
  public void createProjectTaskWithNullName() throws Exception {
    TaskData taskData = createTaskData();
    taskData.setName(null);
    String json = generateJson(taskData);

    mockMvc.perform(post(baseUrl, 1, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(27)
  public void createProjectTaskWithNullCategory() throws Exception {
    TaskData taskData = createTaskData();
    taskData.setCategoryId(null);
    String json = generateJson(taskData);

    mockMvc.perform(post(baseUrl, 1, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(28)
  public void markProjectTaskAsDoneForNonExistingUserProject() throws Exception {
    mockMvc.perform(put(baseUrl + "/{taskId}/complete", 99, 99, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(29)
  public void updateProjectTask() throws Exception {
    TaskData taskData = createTaskData();
    taskData.setName("Updated name");
    String json = generateJson(taskData);

    mockMvc.perform(put(baseUrl + "/{taskId}", 1, 1, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(jsonPath("$.name").value("Updated name"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(30)
  public void getUpdateProjectTask() throws Exception {
    mockMvc.perform(get(baseUrl + "/{taskId}", 1, 1, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value("Updated name"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(29)
  public void updateNonExistingProjectTask() throws Exception {
    TaskData taskData = createTaskData();
    taskData.setName("Updated name");
    String json = generateJson(taskData);

    mockMvc.perform(put(baseUrl + "/{taskId}", 1, 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(30)
  public void updateProjectTaskForNonExistingProject() throws Exception {
    TaskData taskData = createTaskData();
    taskData.setName("Updated name");
    String json = generateJson(taskData);

    mockMvc.perform(put(baseUrl + "/{taskId}", 1, 99, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(31)
  public void updateProjectTaskForNonExistingUser() throws Exception {
    TaskData taskData = createTaskData();
    taskData.setName("Updated name");
    String json = generateJson(taskData);

    mockMvc.perform(put(baseUrl + "/{taskId}", 99, 1, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(32)
  public void updateProjectTaskForNonExistingUserProject() throws Exception {
    TaskData taskData = createTaskData();
    taskData.setName("Updated name");
    String json = generateJson(taskData);

    mockMvc.perform(put(baseUrl + "/{taskId}", 99, 99, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(33)
  public void updateDeletedProjectTask() throws Exception {
    TaskData taskData = createTaskData();
    taskData.setName("Updated name");
    String json = generateJson(taskData);

    mockMvc.perform(put(baseUrl + "/{taskId}", 1, 1, 7)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(34)
  public void getAllProjectTasksForNonExistingProject() throws Exception {
    mockMvc.perform(get(baseUrl, 1, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(35)
  public void getAllProjectTasksForNonExistingUser() throws Exception {
    mockMvc.perform(get(baseUrl, 99, 1)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(36)
  public void getAllProjectTasksForNonExistingUserAndProject() throws Exception {
    mockMvc.perform(get(baseUrl, 99, 99)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(37)
  public void getAllProjectTasksForNonExistingRelationOfUserAndProject() throws Exception {
    mockMvc.perform(get(baseUrl, 2, 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
