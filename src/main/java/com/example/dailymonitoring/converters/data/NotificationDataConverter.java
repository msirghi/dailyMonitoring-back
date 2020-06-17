package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.NotificationData;
import com.example.dailymonitoring.models.entities.NotificationEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Sirghi Mihail
 */
@Component
public class NotificationDataConverter implements Converter<NotificationEntity, NotificationData> {

  @Override
  public NotificationData convert(NotificationEntity source) {
    return NotificationData
        .builder()
        .id(source.getId())
        .message(source.getMessage())
        .type(source.getType())
        .status(source.getStatus())
        .authorName(source.getUser().getFullName())
        .projectName(source.getProject().getName())
        .authorUsername(source.getUser().getUsername())
        .projectId(source.getProject().getId())
        .date(source.getUpdatedAt())
        .authorId(source.getUser().getId())
        .build();
  }
}
