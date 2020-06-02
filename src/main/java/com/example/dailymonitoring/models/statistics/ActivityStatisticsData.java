package com.example.dailymonitoring.models.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Sirghi Mihail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityStatisticsData {

  @JsonProperty("taskStatistics")
  private TaskStatisticsData taskStatistics;

  @JsonProperty("projectTaskActivity")
  private List<ProjectTaskActivityData> projectTaskActivity;

  @JsonProperty("chartMonths")
  private List<String> chartMonths;

  @JsonProperty("chartValues")
  private List<Integer> chartValues;
}
