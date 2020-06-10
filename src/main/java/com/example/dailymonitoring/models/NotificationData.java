package com.example.dailymonitoring.models;

import com.example.dailymonitoring.models.enums.NotificationStatus;
import com.example.dailymonitoring.models.enums.NotificationType;
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
public class NotificationData {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("message")
  private String message;

  @JsonProperty("type")
  private NotificationType type;

  @JsonProperty("status")
  private NotificationStatus status;

  @JsonProperty("projectName")
  private String projectName;

  @JsonProperty("authorName")
  private String authorName;
}
