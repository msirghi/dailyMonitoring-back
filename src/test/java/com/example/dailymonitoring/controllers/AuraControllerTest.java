package com.example.dailymonitoring.controllers;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class AuraControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private final String baseUrl = "/users/{userId}/aura";

  @Test
  @Order(1)
  public void getUserAura() throws Exception {
    mockMvc.perform(get(baseUrl, 1)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.userId").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.auraCount").value(IsNull.notNullValue()))
        .andExpect(status().isOk());
  }

  @Test
  @Order(2)
  public void getUserAuraForNonExistingUser() throws Exception {
    mockMvc.perform(get(baseUrl, 99)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(3)
  public void getUserAuraForUserWithNegativeId() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl, -1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()))
        .hasCause(new ConstraintViolationException(
            "getUserAura.userId: must be greater than or equal to 1",
            new HashSet<>()));
  }

}
