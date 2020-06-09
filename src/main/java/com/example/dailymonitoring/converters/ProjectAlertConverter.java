package com.example.dailymonitoring.converters;

import com.example.dailymonitoring.models.ProjectAlertData;
import com.example.dailymonitoring.models.entities.ProjectAlertEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Sirghi Mihail
 */
@Component
public class ProjectAlertConverter implements Converter<ProjectAlertData, ProjectAlertEntity> {

  @Override
  public ProjectAlertEntity convert(ProjectAlertData source) {
    return ProjectAlertEntity
        .builder()
        .message(source.getMessage())
        .type(source.getType())
        .build();
  }
}
