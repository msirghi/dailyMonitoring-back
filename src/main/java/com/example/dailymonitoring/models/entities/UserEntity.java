package com.example.dailymonitoring.models.entities;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.models.enums.StatusType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "USERS")
@Data
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

  @Column(name = "USERNAME")
  @NotNull
  @Pattern(regexp = Constants.USERNAME_REGEX,
           message = Constants.USERNAME_ERROR)
  private String username;

  @Column(name = "PASSWORD")
  @NotNull
  private String password;

  @Column(name = "FULL_NAME")
  @NotNull
//  @Pattern(regexp = Constants.FULLNAME_REGEX,
//           message = Constants.FULLNAME_ERROR)
  private String fullName;

  @Column(name = "EMAIL")
  @NotNull
  @Pattern(regexp = Constants.EMAIL_REGEX,
           message = Constants.EMAIL_ERROR)
  @Email
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  @NotNull
  private StatusType status;

  @Column(name = "ENABLED")
  private Boolean enabled;

}
