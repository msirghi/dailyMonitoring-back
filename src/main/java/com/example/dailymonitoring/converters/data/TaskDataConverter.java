package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.entities.TaskEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskDataConverter implements Converter<TaskEntity, TaskData> {

  @Override
  public TaskData convert(TaskEntity source) {
    return TaskData
        .builder()
        .id(source.getId())
        .name(source.getName())
        .description(source.getDescription())
        .categoryId(source.getCategoryId())
        .status(source.getStatus())
        .updatedAt(source.getUpdatedAt())
        .build();
  }
}
