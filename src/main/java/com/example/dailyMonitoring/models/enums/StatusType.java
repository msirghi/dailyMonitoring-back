package com.example.dailyMonitoring.models.enums;

import java.util.Arrays;

public enum StatusType {
  ACTIVE("active"),
  INACTIVE("inactive");

  private String value;

  StatusType(String value) {
    this.value = value;
  }

  public static StatusType fromValue(String value) {
    for (StatusType type : values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException(
            "Unknown enum receive type " + value + ", Allowed values are " + Arrays
                    .toString(values()));
  }
}
