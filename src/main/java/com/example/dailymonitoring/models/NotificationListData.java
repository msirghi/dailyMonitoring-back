package com.example.dailymonitoring.models;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationListData {

  @JsonProperty("notificationIdList")
  private List<Long> notificationIdList;
}
