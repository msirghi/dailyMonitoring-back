package com.example.dailymonitoring.models;

import com.example.dailymonitoring.models.enums.TaskStatusType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

//Added

@ApiModel
@Builder
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class TaskData {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  // FIXME: 16.05.2020 Not null
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("categoryId")
  @NotNull
  private Long categoryId;

  //  LocalDateTime randDate = LocalDateTime.of(2017, Month.JULY, 9, 11, 6, 22);
  //  2017-07-09T11:06:22

  @JsonProperty("updatedAt")
  private Timestamp updatedAt;

  @JsonProperty("dates")
  private List<LocalDateTime> dates = new ArrayList<>();

  @JsonProperty("status")
  private TaskStatusType status;

}
