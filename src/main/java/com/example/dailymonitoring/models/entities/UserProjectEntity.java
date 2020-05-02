package com.example.dailymonitoring.models.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name = "USER_PROJECT",
    uniqueConstraints = {
    @UniqueConstraint(columnNames = {"USER_ID", "PROJECT_ID"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectEntity extends BaseEntity {
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", nullable = false)
  UserEntity user;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "PROJECT_ID", nullable = false)
  ProjectEntity project;
}
