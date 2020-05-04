package com.example.dailymonitoring.models;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectData {

  private Long id;

  @NotNull
  private String name;

  private String description;

}
