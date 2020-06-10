package com.example.dailymonitoring.models.entities;

import com.example.dailymonitoring.models.enums.NotificationStatus;
import com.example.dailymonitoring.models.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Sirghi Mihail
 */
@Data
@Entity
@Builder
@Table(name = "NOTIFICATIONS")
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity extends BaseEntity {

  @NotNull
  @Column(name = "MESSAGE")
  private String message;

  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE")
  @NotNull
  private NotificationType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  @NotNull
  private NotificationStatus status;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", nullable = false)
  private UserEntity user;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "PROJECT_ID", nullable = false)
  @NotNull
  private ProjectEntity project;
}
