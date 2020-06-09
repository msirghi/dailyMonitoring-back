package com.example.dailymonitoring.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Sirghi Mihail
 */
@Entity
@Table(name = "AURAS")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuraEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", nullable = false)
  @NotNull
  private UserEntity user;

  @NotNull
  @Column(name = "AURA_COUNT")
  private Long auraCount;
}
