package com.example.dailymonitoring.models.entities;

import com.example.dailymonitoring.models.enums.AlertType;
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
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * @author Sirghi Mihail
 */
@Builder
@Entity
@Table(name = "PROJECT_ALERT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAlertEntity extends BaseEntity {

  @Column(name = "MESSAGE")
  @NotNull
  private String message;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", nullable = false)
  @NotNull
  private UserEntity author;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "PROJECT_ID", nullable = false)
  @NotNull
  private ProjectEntity project;

  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE")
  @NotNull
  private AlertType type;

  @Column(name = "DATE")
  private Date date;
}
