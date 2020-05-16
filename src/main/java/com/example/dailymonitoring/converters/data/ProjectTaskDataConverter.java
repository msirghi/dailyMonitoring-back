package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.entities.ProjectTaskEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Sirghi Mihail
 */
@Component
public class ProjectTaskDataConverter implements Converter<ProjectTaskEntity, TaskData> {
  @Override
  public TaskData convert(ProjectTaskEntity source) {
    return TaskData
        .builder()
        .id(source.getId())
        .name(source.getName())
        .description(source.getDescription())
        .status(source.getStatus())
        .categoryId(source.getCategoryId())
        .build();
  }
}
