package com.example.dailymonitoring.models;

import com.example.dailymonitoring.models.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
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
public class UserData {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("username")
  @NotNull
  @Pattern(regexp = Constants.USERNAME_REGEX,
          message = Constants.USERNAME_ERROR)
  private String username;

  @JsonProperty("email")
  @NotNull
  @Pattern(regexp = Constants.EMAIL_REGEX,
          message = Constants.EMAIL_ERROR)
  private String email;

  @JsonProperty("password")
  @Pattern(regexp = Constants.PASSWORD_REGEX,
          message = Constants.PASSWORD_ERROR)
  private String password;

  @JsonProperty("fullName")
  @NotNull
  @Pattern(regexp = Constants.FULLNAME_REGEX,
          message = Constants.FULLNAME_ERROR)
  private String fullName;

  @JsonProperty("status")
  private StatusType status;

}
