package com.example.dailymonitoring.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.dailymonitoring.models.EmailTemplateData;
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
class EmailTemplateControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private final String baseUrl = "/templates";

  public EmailTemplateData getModel() {
    return EmailTemplateData
        .builder()
        .name("Template name")
        .description("Template description")
        .template("Template")
        .build();
  }

  public String generateJson(EmailTemplateData emailTemplateData) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(emailTemplateData);
  }

  @Test
  @Order(1)
  public void createTemplate() throws Exception {
    String json = generateJson(getModel());

    mockMvc.perform(post(baseUrl)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.name").value("Template name"))
        .andExpect(jsonPath("$.description").value("Template description"))
        .andExpect(jsonPath("$.template").value("Template"))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(2)
  public void getCreatedTemplate() throws Exception {
    String json = generateJson(getModel());

    mockMvc.perform(get(baseUrl + "/{templateId}", 1)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.name").value("Template name"))
        .andExpect(jsonPath("$.description").value("Template description"))
        .andExpect(jsonPath("$.template").value("Template"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(3)
  public void createTemplateWithoutName() throws Exception {
    EmailTemplateData model = getModel();
    model.setName(null);
    String json = generateJson(model);

    mockMvc.perform(post(baseUrl)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(4)
  public void createTemplateWithoutDescription() throws Exception {
    EmailTemplateData model = getModel();
    model.setDescription(null);
    String json = generateJson(model);

    mockMvc.perform(post(baseUrl)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(5)
  public void createTemplateWithoutTemplate() throws Exception {
    EmailTemplateData model = getModel();
    model.setTemplate(null);
    String json = generateJson(model);

    mockMvc.perform(post(baseUrl)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Order(6)
  public void getTemplateWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/{templateId}", -1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest()))
        .hasCause(new ConstraintViolationException(
            "getTemplateById.templateId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(7)
  public void getNonExistingTemplate() throws Exception {
    mockMvc.perform(get(baseUrl + "/{templateId}", 99)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(8)
  public void getAllTemplates() throws Exception {
    mockMvc.perform(get(baseUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(status().isOk());
  }

  @Test
  @Order(9)
  public void updateTemplate() throws Exception {
    String json = generateJson(EmailTemplateData
        .builder()
        .name("New name")
        .description("New description")
        .template("New template")
        .build());

    mockMvc.perform(put(baseUrl + "/{templateId}", 1)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.name").value("New name"))
        .andExpect(jsonPath("$.description").value("New description"))
        .andExpect(jsonPath("$.template").value("New template"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(10)
  public void getUpdatedTemplate() throws Exception {
    mockMvc.perform(get(baseUrl + "/{templateId}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.name").value("New name"))
        .andExpect(jsonPath("$.description").value("New description"))
        .andExpect(jsonPath("$.template").value("New template"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(11)
  public void updateTemplateWithNegativeId() throws Exception {
    String json = generateJson(getModel());

    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(put(baseUrl + "/{templateId}", -1)
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()))
        .hasCause(new ConstraintViolationException(
            "updateTemplate.templateId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(12)
  public void deleteTemplateWithNegativeId() throws Exception {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(delete(baseUrl + "/{templateId}", -1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()))
        .hasCause(new ConstraintViolationException(
            "deleteTemplate.templateId: must be greater than or equal to 1",
            new HashSet<>()));
  }

  @Test
  @Order(13)
  public void deleteNonExistingTemplate() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{templateId}", 99)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(14)
  public void deleteAnExistingTemplate() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{templateId}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(15)
  public void deleteAlreadyDeletedTemplate() throws Exception {
    mockMvc.perform(delete(baseUrl + "/{templateId}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(16)
  public void getDeletedTemplate() throws Exception {
    mockMvc.perform(get(baseUrl + "/{templateId}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(17)
  public void getAllTemplatesAfterDeletion() throws Exception {
    mockMvc.perform(get(baseUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(0)))
        .andExpect(status().isOk());
  }
}