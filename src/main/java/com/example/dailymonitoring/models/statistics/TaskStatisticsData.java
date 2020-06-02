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
public class TaskStatisticsData {

  @JsonProperty("totalProjects")
  private Long totalProjects;

  @JsonProperty("totalProjectsChange")
  private Long totalProjectsChange;

  @JsonProperty("totalTasks")
  private Long totalTasks;

  @JsonProperty("totalTaskChange")
  private Long totalTaskChange;

  @JsonProperty("totalDoneTasks")
  private Long totalDoneTasks;

  @JsonProperty("totalDoneTaskChange")
  private Long totalDoneTaskChange;

  @JsonProperty("totalUndoneTasks")
  private Long totalUndoneTasks;

  @JsonProperty("totalUndoneTaskChange")
  private Long totalUndoneTaskChange;

}
