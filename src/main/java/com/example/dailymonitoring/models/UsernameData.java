package com.example.dailymonitoring.models;

import com.example.dailymonitoring.constants.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@ApiModel
@Builder
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class UsernameData {

  @JsonProperty("username")
  @NotNull
  @Pattern(regexp = Constants.USERNAME_REGEX,
      message = Constants.USERNAME_ERROR)
  private String username;

}
