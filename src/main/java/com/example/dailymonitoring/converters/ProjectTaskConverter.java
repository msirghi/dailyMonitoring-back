package com.example.dailymonitoring.converters;

import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.entities.ProjectTaskEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Sirghi Mihail
 */
@Component
public class ProjectTaskConverter implements Converter<TaskData, ProjectTaskEntity> {

  @Override
  public ProjectTaskEntity convert(TaskData source) {
    return ProjectTaskEntity
        .builder()
        .name(source.getName())
        .description(source.getDescription())
        .build();
  }
}
