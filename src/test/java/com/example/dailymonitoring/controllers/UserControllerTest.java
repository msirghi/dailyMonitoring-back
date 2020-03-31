package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.models.EmailData;
import com.example.dailymonitoring.models.PasswordData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.UsernameData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
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
        .password("password1")
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

  public String generateJsonEmail(EmailData emailData) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(emailData);
  }

  public String generateJsonUsername(UsernameData usernameData) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(usernameData);
  }

  public String generateJsonPassword(PasswordData passwordData) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(passwordData);
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

  @Test
  @Order(12)
  public void userCreateWithTakenEmail() throws Exception {
    UserData userData = createUserModel();
    userData.setEmail("test@test.com");
    String json = generateJson(null);
    mockMvc.perform(post(baseUrl)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }

  @Test
  @Order(13)
  public void userCreateWithUnsuitableUsername() throws Exception {
    UserData userData = createUserModel();
    userData.setUsername("Wrong data");
    userData.setEmail("vasea@gmail.com");
    String json = generateJson(userData);
    mockMvc.perform(post(baseUrl)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));

  }

  @Test
  @Order(14)
  public void userCreateWithUnsuitablePassword() throws Exception {
    UserData userData = createUserModel();
    userData.setPassword("Valai");
    userData.setEmail("pupkin332@gmail.com");
    userData.setUsername("Sachamun");
    String json = generateJson(userData);
    mockMvc.perform(post(baseUrl)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));


  }

  @Test
  @Order(15)
  public void userCreateWithUnsuitableEmail() throws Exception {
    UserData userData = createUserModel();
    userData.setEmail("Valai@notvalid");
    userData.setUsername("TolikVadila");
    String json = generateJson(userData);
    mockMvc.perform(post(baseUrl)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));

  }

  @Test
  @Order(16)
  public void userCreateWithUnsuitableFullName() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("Azaza3  Vasea");
    userData.setEmail("Love4you@gmail.com");
    userData.setUsername("IntoarceCalul");
    String json = generateJson(userData);
    mockMvc.perform(post(baseUrl)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }

  @Test
  @Order(17)
  public void userCreateForUpdateTest() throws Exception {
    UserData userData = createUserModel();
    userData.setEmail("testemailsecond@gmail.com");
    userData.setUsername("IWannaFly");

    String json = generateJson(userData);

    getUserDataPattern(mockMvc.perform(post(baseUrl)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)))
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.password").value(""));
  }

  @Test
  @Order(18)
  public void userUpdateEmailFullNameUsername() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("Test Test");
    userData.setEmail("test1@test.com");
    userData.setUsername("username2");
    userData.setPassword("Password3");
    String json = generateJson(userData);
    mockMvc.perform(put("/users/{id}", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("test1@test.com"))
        .andExpect(jsonPath("$.username").value("username2"))
        .andExpect(jsonPath("$.fullName").value("Test Test"));
  }

  @Test
  @Order(19)
  public void userUpdateEmailFullNameUsernameWithUnsuitableEmail() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("Test Test Test");
    userData.setEmail("test1@test");
    userData.setUsername("username22");
    userData.setPassword("Password3");
    String json = generateJson(userData);
    mockMvc.perform(put("/users/{id}", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }


  @Test
  @Order(20)
  public void userUpdateEmailFullNameUsernameWithUnsuitableUsername() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("Test Test Test");
    userData.setEmail("test13@test.com");
    userData.setUsername("user 22");
    userData.setPassword("Password3");
    String json = generateJson(userData);
    mockMvc.perform(put("/users/{id}", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }


  @Test
  @Order(21)
  public void userUpdateEmailFullNameUsernameWithUnsuitableFullName() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("Test  Test13");
    userData.setEmail("test13@test.com");
    userData.setUsername("user22");
    userData.setPassword("Password3");
    String json = generateJson(userData);
    mockMvc.perform(put("/users/{id}", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }


  @Test
  @Order(22)
  public void userUpdateEmailFullNameUsernameWithoutEmail() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("Test  Test13");
    userData.setEmail("");
    userData.setUsername("user22");
    userData.setPassword("Password3");
    String json = generateJson(userData);
    mockMvc.perform(put("/users/{id}", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }


  @Test
  @Order(23)
  public void userUpdateEmailFullNameUsernameWithoutUsername() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("Test  Test13");
    userData.setEmail("testemail@gmail.com");
    userData.setUsername("");
    userData.setPassword("Password3");
    String json = generateJson(userData);
    mockMvc.perform(put("/users/{id}", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }


  @Test
  @Order(24)
  public void userUpdateEmailFullNameUsernameWithoutFullName() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("");
    userData.setEmail("testemail@gmail.com");
    userData.setUsername("IDontKnow");
    userData.setPassword("Password3");
    String json = generateJson(userData);
    mockMvc.perform(put("/users/{id}", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }

  @Test
  @Order(25)
  public void userUpdateEmailFullNameUsernameForUnexistentUser() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("Test Test Test");
    userData.setEmail("testemail@gmail.com");
    userData.setUsername("IDontKnow");
    userData.setPassword("Password3");
    String json = generateJson(userData);
    mockMvc.perform(put("/users/{id}", 10)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(26)
  public void userUpdateEmailFullNameUsernameWithTakenEmail() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("Test Data");
    userData.setEmail("test1@test.com");
    userData.setUsername("IDontKnow");
    userData.setPassword("Password3");
    String json = generateJson(userData);
    mockMvc.perform(put("/users/{id}", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }

  @Test
  @Order(27)
  public void userUpdateEmailFullNameUsernameWithTakenUsername() throws Exception {
    UserData userData = createUserModel();
    userData.setFullName("Test Data");
    userData.setEmail("test142@test.com");
    userData.setUsername("username2");
    userData.setPassword("Password3");
    String json = generateJson(userData);
    mockMvc.perform(put("/users/{id}", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }

  @Test
  @Order(28)
  public void userUpdateEmailOnly() throws Exception {
    EmailData emailData = new EmailData();
    emailData.setEmail("test142@test.com");
    String json = generateJsonEmail(emailData);
    mockMvc.perform(put("/users/{id}/resetEmail", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk());
  }


  @Test
  @Order(29)
  public void userUpdateEmailOnlyWithUnsuitableEmail() throws Exception {
    EmailData emailData = new EmailData();
    emailData.setEmail("test142@test");
    String json = generateJsonEmail(emailData);
    mockMvc.perform(put("/users/{id}/resetEmail", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }

  @Test
  @Order(30)
  public void userUpdateEmailOnlyWithTakenEmail() throws Exception {
    EmailData emailData = new EmailData();
    emailData.setEmail("test@test.com");
    String json = generateJsonEmail(emailData);
    mockMvc.perform(put("/users/{id}/resetEmail", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }


  @Test
  @Order(31)
  public void userUpdateEmailOnlyWithEmptyEmail() throws Exception {
    EmailData emailData = new EmailData();
    emailData.setEmail("");
    String json = generateJsonEmail(emailData);
    mockMvc.perform(put("/users/{id}/resetEmail", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }

  @Test
  @Order(32)
  public void userUpdateUsernameOnly() throws Exception {
    UsernameData usernameData = new UsernameData();
    usernameData.setUsername("usertest222");
    String json = generateJsonUsername(usernameData);
    mockMvc.perform(put("/users/{id}/resetUsrName", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk());
  }

  @Test
  @Order(33)
  public void userUpdateUsernameOnlyWithUnsuitableUsername() throws Exception {
    UsernameData usernameData = new UsernameData();
    usernameData.setUsername("usertest 222");
    String json = generateJsonUsername(usernameData);
    mockMvc.perform(put("/users/{id}/resetUsrName", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }

  @Test
  @Order(34)
  public void userUpdateUsernameOnlyWithEmptyUsername() throws Exception {
    UsernameData usernameData = new UsernameData();
    usernameData.setUsername("");
    String json = generateJsonUsername(usernameData);
    mockMvc.perform(put("/users/{id}/resetUsrName", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }

  @Test
  @Order(35)
  public void userUpdateUsernameOnlyWithTakenUsername() throws Exception {
    UsernameData usernameData = new UsernameData();
    usernameData.setUsername("username1");
    String json = generateJsonUsername(usernameData);
    mockMvc.perform(put("/users/{id}/resetUsrName", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(400));
  }

  @Test
  @Order(36)
  public void userUpdatePasswordOnly() throws Exception {
    PasswordData passwordData = new PasswordData();
    passwordData.setPassword("ribkaSveta3");
    passwordData.setOldpassword("password1");
    String json = generateJsonPassword(passwordData);
    mockMvc.perform(put("/users/{id}/resetPwd", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk());
  }

  @Test
  @Order(37)
  public void userUpdatePasswordWithEmptyOldPassword() throws Exception {
    PasswordData passwordData = new PasswordData();
    passwordData.setPassword("passwordnew1");
    passwordData.setOldpassword("");
    String json = generateJsonPassword(passwordData);
    mockMvc.perform(put("/users/{id}/resetPwd", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(404));
  }

  @Test
  @Order(38)
  public void userUpdatePasswordWithEmptyPassword() throws Exception {
    PasswordData passwordData = new PasswordData();
    passwordData.setPassword("");
    passwordData.setOldpassword("password1");
    String json = generateJsonPassword(passwordData);
    mockMvc.perform(put("/users/{id}/resetPwd", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(404));
  }

  @Test
  @Order(39)
  public void userUpdatePasswordWithEmptyPasswordAndOldPassword() throws Exception {
    PasswordData passwordData = new PasswordData();
    passwordData.setPassword("");
    passwordData.setOldpassword("");
    String json = generateJsonPassword(passwordData);
    mockMvc.perform(put("/users/{id}/resetPwd", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(404));
  }

  @Test
  @Order(40)
  public void userUpdatePasswordWitUsedPassword() throws Exception {
    PasswordData passwordData = new PasswordData();
    passwordData.setPassword("ribkaSveta3");
    passwordData.setOldpassword("ribkaSveta3");
    String json = generateJsonPassword(passwordData);
    mockMvc.perform(put("/users/{id}/resetPwd", 2)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().is(200));
  }
}
