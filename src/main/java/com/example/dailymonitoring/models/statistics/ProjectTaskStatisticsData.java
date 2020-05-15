package com.example.dailymonitoring.models.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sirghi Mihail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectTaskStatisticsData {

  @JsonProperty("userId")
  private Long userId;

  @JsonProperty("fullName")
  private String fullName;

  @JsonProperty("tasksDone")
  private Long tasksDone;

  @JsonProperty("taskPercentage")
  private Long taskPercentage;
}
