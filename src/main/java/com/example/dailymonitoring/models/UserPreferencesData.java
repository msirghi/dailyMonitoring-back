package com.example.dailymonitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Sirghi Mihail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferencesData {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("dailyTaskCount")
  @NotNull
  private Long dailyTaskCount;
}
