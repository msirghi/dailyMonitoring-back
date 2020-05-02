package com.example.dailymonitoring.converters;

import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.entities.ProjectEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter implements Converter<ProjectData, ProjectEntity> {

  @Override
  public ProjectEntity convert(ProjectData source) {
    return ProjectEntity
        .builder()
        .name(source.getName())
        .description(source.getDescription())
        .build();
  }
}
