package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.entities.ProjectEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectDataConverter implements Converter<ProjectEntity, ProjectData> {

  @Override
  public ProjectData convert(ProjectEntity source) {
    return ProjectData
        .builder()
        .id(source.getId())
        .name(source.getName())
        .description(source.getDescription())
        .build();
  }
}
