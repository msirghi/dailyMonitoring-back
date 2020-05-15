package com.example.dailymonitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleData {

  @JsonProperty("name")
  @NotNull
  private String name;

  @JsonProperty("userId")
  @NotNull
  private Long userId;

}
