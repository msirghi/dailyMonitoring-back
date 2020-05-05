package com.example.dailymonitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskData {

  @JsonProperty("user")
  UserData user;

  @JsonProperty("task")
  TaskData task;

}
