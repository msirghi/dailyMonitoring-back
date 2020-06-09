package com.example.dailymonitoring.models;

import com.example.dailymonitoring.models.enums.AlertType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * @author Sirghi Mihail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectAlertData {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("authorId")
  private Long authorId;

  @JsonProperty("authorName")
  private String authorName;

  @JsonProperty("message")
  @NotNull
  private String message;

  @JsonProperty("areMembersNotified")
  private Boolean areMembersNotified;

  @JsonProperty("type")
  @NotNull
  private AlertType type;

  @JsonProperty("date")
  private Date date;
}
