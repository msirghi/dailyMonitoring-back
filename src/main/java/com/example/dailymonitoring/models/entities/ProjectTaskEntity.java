package com.example.dailymonitoring.models.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PROJECT_TASK",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"PROJECT_ID", "TASK_ID"})
    }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTaskEntity extends BaseEntity {

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "TASK_ID", nullable = false)
  TaskEntity task;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "PROJECT_ID", nullable = false)
  ProjectEntity project;
}
