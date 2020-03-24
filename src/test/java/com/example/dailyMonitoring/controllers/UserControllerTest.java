package com.example.dailyMonitoring.controllers;

import com.example.dailyMonitoring.models.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.core.IsNull;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private final String baseUrl = "/users";

  public UserData createUserModel() {
    return UserData
            .builder()
            .username("username1")
            .password("pwd")
            .email("test@test.com")
            .fullName("Test")
            .build();
  }

  public String generateJson(UserData userData) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(userData);
  }

  private ResultActions getUserDataPattern(ResultActions resultActions) throws Exception {

    return resultActions
            .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
            .andExpect(jsonPath("$.username").value(IsNull.notNullValue()))
            .andExpect(jsonPath("$.fullName").isString())
            .andExpect(jsonPath("$.status").isString())
            .andExpect(jsonPath("$.email").isString());
  }


  @Test
  @Order(1)
  public void userCreate() throws Exception {
    String json = generateJson(createUserModel());

    getUserDataPattern(mockMvc.perform(post(baseUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.password").value(""));
  }

  @Test
  @Order(2)
  public void userCreateWithoutUsername() throws Exception {
    UserData userData = createUserModel();
    userData.setUsername(null);
    String json = generateJson(null);
    mockMvc.perform(post(baseUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().is(400));
  }

  @Test
  @Order(3)
  public void userCreateWithoutPassword() throws Exception {
    UserData userData = createUserModel();
    userData.setPassword(null);
    String json = generateJson(null);
    mockMvc.perform(post(baseUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().is(400));
  }

  @Test
  @Order(3)
  public void userCreateWithoutFullName() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName(null);
    String json = generateJson(null);
    mockMvc.perform(post(baseUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().is(400));
  }

  @Test
  @Order(4)
  public void userCreateWithoutEmail() throws Exception {
    UserData userData = createUserModel();
    userData.setEmail(null);
    String json = generateJson(null);
    mockMvc.perform(post(baseUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().is(400));
  }

  @Test
  @Order(5)
  public void userCreateWithWrongEmail() throws Exception {
    UserData userData = createUserModel();
    userData.setEmail("test");
    String json = generateJson(null);
    mockMvc.perform(post(baseUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().is(400));
  }

  @Test
  @Order(6)
  public void userCreateWithTakenUsername() throws Exception {
    UserData userData = createUserModel();
    userData.setUsername("username1");
    String json = generateJson(null);
    mockMvc.perform(post(baseUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().is(400));
  }

  @Test
  @Order(7)
  public void userGet() throws Exception {
    UserData userData = createUserModel();
    getUserDataPattern(mockMvc.perform(get(baseUrl + "/{id}", 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()))
            .andExpect(jsonPath("$.username").value(userData.getUsername()))
            .andExpect(jsonPath("$.fullName").value(userData.getFullName()))
            .andExpect(jsonPath("$.status").value("ACTIVE"))
            .andExpect(jsonPath("$.email").value(userData.getEmail()));
  }

  @Test
  @Order(8)
  public void userGetWithNotFound() throws Exception {
    mockMvc.perform(get(baseUrl + "/{id}", 12)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  @Order(9)
  public void userDelete() throws Exception {
    mockMvc.perform(delete("/users/{id}", 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(204));
  }

  @Test
  @Order(10)
  public void userDeleteWithInactiveStatus() throws Exception {
    mockMvc.perform(delete("/users/{id}", 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  @Order(11)
  public void nonExistingUserDelete() throws Exception {
    mockMvc.perform(delete("/users/{id}", 10)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }
}
