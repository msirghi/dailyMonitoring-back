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
  private String username;

  @Column(name = "PASSWORD")
  @NotNull
  private String password;

  @Column(name = "FULL_NAME")
  @NotNull
  private String fullName;

  @Column(name = "EMAIL")
  @NotNull
  @Email
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  @NotNull
  private StatusType status;

  //  TODO: ADD TASKS ARRAY
}
