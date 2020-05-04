package com.example.dailymonitoring.models.entities;

import com.example.dailymonitoring.models.enums.TaskStatusType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "TASKS")
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "USER_ID")
  private UserEntity user;

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

}
