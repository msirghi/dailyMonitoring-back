package com.example.dailymonitoring.models.entities;

import com.example.dailymonitoring.models.enums.TaskStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PROJECT_TASKS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTaskEntity extends BaseEntity {

  @Column(name = "NAME")
  @NotNull
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "CATEGORY_ID")
  private Long categoryId;

  @Column(name = "DATE")
  private LocalDateTime date;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  @NotNull
  private TaskStatusType status;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "PROJECT_ID", nullable = false)
  private ProjectEntity project;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "DONE_BY_USER_ID")
  private UserEntity taskDoneBy;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "CREATOR_USER_ID", nullable = false)
  private UserEntity taskCreator;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ASSIGNED_TO_USER_ID", nullable = false)
  private UserEntity taskAssignedTo;
}
