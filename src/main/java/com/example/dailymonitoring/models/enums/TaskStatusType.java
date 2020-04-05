package com.example.dailymonitoring.models.enums;

import java.util.Arrays;

public enum TaskStatusType {
  DONE("done"),
  INPROGRESS("inprogress"),
  UNDONE("undone"),
  DELETED("deleted");

  private String value;

  TaskStatusType(String value) {
    this.value = value;
  }

  public static TaskStatusType fromValue(String value) {
    for (TaskStatusType type : values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException(
        "Unknown enum receive type " + value + ", Allowed values are " + Arrays
            .toString(values()));
  }
}

