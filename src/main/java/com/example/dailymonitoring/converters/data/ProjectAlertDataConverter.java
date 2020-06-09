package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.ProjectAlertData;
import com.example.dailymonitoring.models.entities.ProjectAlertEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Sirghi Mihail
 */
@Component
public class ProjectAlertDataConverter implements Converter<ProjectAlertEntity, ProjectAlertData> {

  @Override
  public ProjectAlertData convert(ProjectAlertEntity source) {
    return ProjectAlertData
        .builder()
        .id(source.getId())
        .authorId(source.getAuthor().getId())
        .authorName(source.getAuthor().getFullName())
        .message(source.getMessage())
        .type(source.getType())
        .date(source.getDate())
        .build();
  }
}
