package com.example.dailymonitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectTaskData {

  @JsonProperty("tasks")
  private List<TaskData> taskData;

  @JsonProperty("project")
  private ProjectData projectData;
}
