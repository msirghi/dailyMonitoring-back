package com.example.dailymonitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sirghi Mihail
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuraData {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("userId")
  private Long userId;

  @JsonProperty("auraCount")
  private Long auraCount;
}
