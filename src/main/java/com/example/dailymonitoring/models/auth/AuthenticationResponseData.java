package com.example.dailymonitoring.models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponseData {

  @JsonProperty("jwt")
  private final String jwt;

  @JsonProperty("refreshToken")
  @NotNull
  private final String refreshToken;

}
