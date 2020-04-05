package com.example.dailymonitoring.converters;

import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.entities.TaskEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter implements Converter<TaskData, TaskEntity> {
  @Override
  public TaskEntity convert(TaskData source) {
    return TaskEntity
        .builder()
        .name(source.getName())
        .description(source.getDescription())
        .categoryId(source.getCategoryId())
        .build();
  }
}
