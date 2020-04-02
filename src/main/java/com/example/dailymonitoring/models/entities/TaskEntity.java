package com.example.dailymonitoring.models.entities;


import com.example.dailymonitoring.models.enums.StatusType;
import com.example.dailymonitoring.models.enums.TaskStatusType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

//Added

@Entity
@Table(name = "TASKS")
@Data
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

  @ManyToOne(cascade = CascadeType.ALL)
  private UserEntity user;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "NAME")
  @NotNull
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "CATEGORY_ID")
  private Long categoryId;

  @Column(name = "DELETED")
  private boolean deleted;

  @Column(name = "DATES")
  private List<LocalDateTime> dates;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  @NotNull
  private TaskStatusType status;



}
