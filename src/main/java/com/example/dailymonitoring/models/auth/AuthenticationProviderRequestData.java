package com.example.dailymonitoring.models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Sirghi Mihail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationProviderRequestData {

  @JsonProperty("externalId")
  @NotNull
  private String externalId;

  @JsonProperty("email")
  @NotNull
  private String email;

}
