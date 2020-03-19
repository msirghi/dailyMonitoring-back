package com.example.dailyMonitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {

  @JsonProperty("code")
  private Integer code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("description")
  private String description;

  @JsonProperty("infoURL")
  private String infoUrl;
}
