package com.example.dailymonitoring.models.enums;

import java.util.Arrays;

public enum RoleType {
  ADMIN("admin"),
  USER("user");

  private String value;

  RoleType(String value) {
    this.value = value;
  }

  public static RoleType fromValue(String value) {
    for (RoleType type : values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException(
        "Unknown enum receive type " + value + ", Allowed values are " + Arrays
            .toString(values()));
  }
}
