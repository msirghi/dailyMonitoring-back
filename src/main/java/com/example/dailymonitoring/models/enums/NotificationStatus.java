package com.example.dailymonitoring.models.enums;

import java.util.Arrays;

/**
 * @author Sirghi Mihail
 */
public enum NotificationStatus {
  READ("read"),
  UNREAD("unread");

  private String value;

  NotificationStatus(String value) {
    this.value = value;
  }

  public static NotificationStatus fromValue(String value) {
    for (NotificationStatus type : values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException(
        "Unknown enum receive type " + value + ", Allowed values are " + Arrays
            .toString(values()));
  }
}
