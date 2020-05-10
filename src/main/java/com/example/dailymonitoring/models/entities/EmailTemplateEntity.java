package com.example.dailymonitoring.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMAIL_TEMPLATES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateEntity extends BaseEntity {

  @Column(name = "NAME")
  @NotNull
  private String name;

  @Column(name = "DESCRIPTION")
  @NotNull
  private String description;

  @Column(name = "TEMPLATE")
  @NotNull
  private String template;
}
