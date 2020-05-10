package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.EmailTemplateData;
import com.example.dailymonitoring.models.entities.EmailTemplateEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateDataConverter implements
    Converter<EmailTemplateEntity, EmailTemplateData> {

  @Override
  public EmailTemplateData convert(EmailTemplateEntity source) {
    return EmailTemplateData
        .builder()
        .id(source.getId())
        .name(source.getName())
        .description(source.getDescription())
        .template(source.getTemplate())
        .build();
  }
}
