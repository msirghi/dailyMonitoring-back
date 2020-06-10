package com.example.dailymonitoring.models.enums;

import java.util.Arrays;

/**
 * @author Sirghi Mihail
 */
public enum NotificationType {
  PROJECT_TYPE("project type"),
  SERVER("server type");

  private String value;

  NotificationType(String value) {
    this.value = value;
  }

  public static NotificationType fromValue(String value) {
    for (NotificationType type : values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException(
        "Unknown enum receive type " + value + ", Allowed values are " + Arrays
            .toString(values()));
  }
}
