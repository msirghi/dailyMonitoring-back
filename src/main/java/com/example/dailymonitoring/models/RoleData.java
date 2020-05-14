package com.example.dailymonitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleData {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  @NotNull
  private String name;
}
