package com.example.dailymonitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUserData {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("userEmail")
  @NotNull
  private String userEmail;

  @JsonProperty("userFullName")
  private String userFullName;

  @JsonProperty("projectId")
  @NotNull
  private Long projectId;


  @JsonProperty("message")
  private String message;
}
