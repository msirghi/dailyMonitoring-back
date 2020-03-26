package com.example.dailyMonitoring.models.entities;

import com.example.dailyMonitoring.models.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "USERS")
@Data
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "USERNAME")
  @NotNull
//  @Pattern(regexp = "\"^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$\"",
//          message = "Username can contain only alphabetic , numeric , . and _ characters.")
  private String username;

  @Column(name = "PASSWORD")
  @NotNull
  @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
          message = "Minimum eight characters, at least one letter and one number.")
  private String password;

  @Column(name = "FULL_NAME")
  @NotNull
//  @Pattern(regexp = "\"^([a-zA-Z]+|[a-zA-Z]+\\s{1}[a-zA-Z]{1,}|[a-zA-Z]+\\s{1}[a-zA-Z]{3,}\\s{1}[a-zA-Z]{1,})$\"",
//          message = "Letters only , one space between words , Max : First name , last name , patronymic , Min : First name.")
  private String fullName;

  @Column(name = "EMAIL")
  @NotNull
//  @Pattern(regexp = "\".+@.+\\..+\"", message = "Email must be like example@expample.com")
  @Email
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  @NotNull
  private StatusType status;

}
