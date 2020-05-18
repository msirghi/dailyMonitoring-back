package com.example.dailymonitoring.models.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsData {

  @JsonProperty("pieStatistics")
  private List<ProjectTaskStatisticsData> pieStatisticsData;

  @JsonProperty("projectTaskCount")
  private Long allProjectTaskCount;

  @JsonProperty("doneProjectTaskCount")
  private Long allDoneProjectTasks;

  @JsonProperty("undoneProjectTaskCount")
  private Long allUnDoneProjectTasks;
}
