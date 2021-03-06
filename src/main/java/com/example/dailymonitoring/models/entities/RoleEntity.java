package com.example.dailymonitoring.models.entities;

import com.example.dailymonitoring.models.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ROLES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "NAME")
  @NotNull
  private RoleType name;

  @OneToOne(mappedBy = "role")
  private UserEntity userEntity;

}
