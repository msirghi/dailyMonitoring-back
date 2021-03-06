package com.example.dailymonitoring.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
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
public class StatisticsControllerTest {

  private final String baseUrl = "/statistics";

  @Autowired
  private MockMvc mockMvc;

  @Test
  @Order(1)
  public void getUserStatisticsForCurrentYear() throws Exception {
    mockMvc.perform(get(baseUrl + "/users")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.year").value(Calendar.getInstance().get(Calendar.YEAR)))
        .andExpect(jsonPath("$.total").value(6))
        .andExpect(jsonPath("$.progression").value(0.0))
        .andExpect(jsonPath("$.perMonth").exists())
        .andExpect(jsonPath("$.perMonth.january").exists())
        .andExpect(jsonPath("$.perMonth.february").exists())
        .andExpect(jsonPath("$.perMonth.march").exists())
        .andExpect(jsonPath("$.perMonth.april").exists())
        .andExpect(jsonPath("$.perMonth.may").exists())
        .andExpect(jsonPath("$.perMonth.june").exists())
        .andExpect(jsonPath("$.perMonth.july").exists())
        .andExpect(jsonPath("$.perMonth.august").exists())
        .andExpect(jsonPath("$.perMonth.september").exists())
        .andExpect(jsonPath("$.perMonth.october").exists())
        .andExpect(jsonPath("$.perMonth.november").exists())
        .andExpect(jsonPath("$.perMonth.december").exists())
        .andExpect(status().isOk());
  }

  @Test
  @Order(2)
  public void getUserStatisticsForNextYear() throws Exception {
    int nextYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
    mockMvc.perform(get(baseUrl + "/users?year={year}", nextYear)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.year").value(nextYear))
        .andExpect(jsonPath("$.total").value(0))
        .andExpect(jsonPath("$.progression").value(0.0))
        .andExpect(status().isOk());
  }

  @Test
  @Order(3)
  public void getUserStatisticsForYearUnder2020() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/users?year={year}", 2018)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "getUsersStatistics.selectedYear: must be greater than or equal to 2020",
            new HashSet<>()));
  }

  @Test
  @Order(4)
  public void getTasksStatisticsForCurrentYear() throws Exception {
    mockMvc.perform(get(baseUrl + "/tasks")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.year").value(Calendar.getInstance().get(Calendar.YEAR)))
        .andExpect(jsonPath("$.total").value(6))
        .andExpect(jsonPath("$.progression").value(100.0))
        .andExpect(jsonPath("$.perMonth").exists())
        .andExpect(jsonPath("$.perMonth.january").exists())
        .andExpect(jsonPath("$.perMonth.february").exists())
        .andExpect(jsonPath("$.perMonth.march").exists())
        .andExpect(jsonPath("$.perMonth.april").exists())
        .andExpect(jsonPath("$.perMonth.may").exists())
        .andExpect(jsonPath("$.perMonth.june").exists())
        .andExpect(jsonPath("$.perMonth.july").exists())
        .andExpect(jsonPath("$.perMonth.august").exists())
        .andExpect(jsonPath("$.perMonth.september").exists())
        .andExpect(jsonPath("$.perMonth.october").exists())
        .andExpect(jsonPath("$.perMonth.november").exists())
        .andExpect(jsonPath("$.perMonth.december").exists())
        .andExpect(status().isOk());
  }

  @Test
  @Order(5)
  public void getTasksStatisticsForNextYear() throws Exception {
    int nextYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
    mockMvc.perform(get(baseUrl + "/tasks?year={year}", nextYear)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.year").value(nextYear))
        .andExpect(jsonPath("$.total").value(0))
        .andExpect(jsonPath("$.progression").value(0.0))
        .andExpect(status().isOk());
  }

  @Test
  @Order(6)
  public void getTasksStatisticsForYearUnder2020() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/tasks?year={year}", 2018)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "getTasksStatistics.selectedYear: must be greater than or equal to 2020",
            new HashSet<>()));
  }

  @Test
  @Order(7)
  public void getProjectsStatisticsForYearUnder2020() {
    Assertions.assertThatThrownBy(() ->
        mockMvc.perform(get(baseUrl + "/projects?year={year}", 2018)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new ConstraintViolationException(
            "getProjectsStatistics.selectedYear: must be greater than or equal to 2020",
            new HashSet<>()));
  }

  @Test
  @Order(8)
  public void getProjectsStatisticsForNextYear() throws Exception {
    int nextYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
    mockMvc.perform(get(baseUrl + "/projects?year={year}", nextYear)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.year").value(nextYear))
        .andExpect(jsonPath("$.total").value(0))
        .andExpect(jsonPath("$.progression").value(0.0))
        .andExpect(status().isOk());
  }

  @Test
  @Order(9)
  public void getProjectsStatisticsForCurrentYear() throws Exception {
    mockMvc.perform(get(baseUrl + "/projects")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.year").value(Calendar.getInstance().get(Calendar.YEAR)))
        .andExpect(jsonPath("$.total").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.progression").value(IsNull.notNullValue()))
        .andExpect(jsonPath("$.perMonth").exists())
        .andExpect(jsonPath("$.perMonth.january").exists())
        .andExpect(jsonPath("$.perMonth.february").exists())
        .andExpect(jsonPath("$.perMonth.march").exists())
        .andExpect(jsonPath("$.perMonth.april").exists())
        .andExpect(jsonPath("$.perMonth.may").exists())
        .andExpect(jsonPath("$.perMonth.june").exists())
        .andExpect(jsonPath("$.perMonth.july").exists())
        .andExpect(jsonPath("$.perMonth.august").exists())
        .andExpect(jsonPath("$.perMonth.september").exists())
        .andExpect(jsonPath("$.perMonth.october").exists())
        .andExpect(jsonPath("$.perMonth.november").exists())
        .andExpect(jsonPath("$.perMonth.december").exists())
        .andExpect(status().isOk());
  }
}