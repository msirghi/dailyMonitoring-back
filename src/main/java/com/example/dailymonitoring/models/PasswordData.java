package com.example.dailymonitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotNull;
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
public class PasswordData {

  @JsonProperty("password")
  @NotNull
  //    @Pattern(regexp = Constants.PASSWORD_REGEX,
  //            message = Constants.PASSWORD_ERROR)
  private String password;

  @JsonProperty("oldpassword")
  @NotNull
  //    @Pattern(regexp = Constants.PASSWORD_REGEX,
  //            message = Constants.PASSWORD_ERROR)
  private String oldpassword;

}
