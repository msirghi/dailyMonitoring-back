package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserProjectDataConverter implements Converter<UserProjectEntity, ProjectData> {

  @Override
  public ProjectData convert(UserProjectEntity source) {
    return ProjectData
        .builder()
        .id(source.getProject().getId())
        .name(source.getProject().getName())
        .description(source.getProject().getDescription())
        .color(source.getProject().getColor())
        .orderNumber(source.getOrderNumber())
        .build();
  }
}
