package com.example.dailymonitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sirghi Mihail
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProviderData {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("fullName")
  private String fullName;

  @JsonProperty("idToken")
  private String idToken;

  @JsonProperty("username")
  private String username;

  @JsonProperty("provider")
  private String provider;

  @JsonProperty("email")
  private String email;

  @JsonProperty("externalId")
  private String externalId;
}
