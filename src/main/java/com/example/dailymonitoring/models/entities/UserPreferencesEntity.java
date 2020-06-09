package com.example.dailymonitoring.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Sirghi Mihail
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "USER_PREFERENCES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferencesEntity extends BaseEntity {

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", nullable = false)
  @NotNull
  private UserEntity user;

  @Column(name = "DAILY_TASK_COUNT")
  @NotNull
  private Long dailyTaskCount;
}
