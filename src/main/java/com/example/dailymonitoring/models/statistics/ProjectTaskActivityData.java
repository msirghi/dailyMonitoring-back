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
public class ProjectTaskActivityData {

  @JsonProperty("userName")
  private String userName;

  @JsonProperty("userId")
  private Long userId;

  @JsonProperty("projectName")
  private String projectName;

  @JsonProperty("projectId")
  private Long projectId;

  @JsonProperty("task")
  private String task;

  @JsonProperty("dateTime")
  private String dateTime;
}
