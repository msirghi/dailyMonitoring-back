package com.example.dailymonitoring.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseData {

  private final String jwt;
}
