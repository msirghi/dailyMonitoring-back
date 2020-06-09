package com.example.dailymonitoring.models.enums;

import java.util.Arrays;

/**
 * @author Sirghi Mihail
 */
public enum AlertType {
  INFO("info"),
  WARNING("warning"),
  DANGER("danger"),
  SUCCESS("success");

  private String value;

  AlertType(String value) {
    this.value = value;
  }

  public static AlertType fromValue(String value) {
    for (AlertType type : values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException(
        "Unknown enum receive type " + value + ", Allowed values are " + Arrays
            .toString(values()));
  }
}
