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
public class EmailTemplateData {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  @NotNull
  private String name;

  @JsonProperty("description")
  @NotNull
  private String description;

  @JsonProperty("template")
  @NotNull
  private String template;
}
