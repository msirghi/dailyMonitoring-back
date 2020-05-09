package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.ProjectUserData;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectUserDataConverter implements Converter<UserProjectEntity, ProjectUserData> {

  @Override
  public ProjectUserData convert(UserProjectEntity source) {
    return ProjectUserData.builder()
        .projectId(source.getProject().getId())
        .userEmail(source.getUser().getEmail())
        .userFullName(source.getUser().getFullName())
        .build();
  }
}
