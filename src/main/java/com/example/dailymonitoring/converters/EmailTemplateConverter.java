package com.example.dailymonitoring.converters;

import com.example.dailymonitoring.models.EmailTemplateData;
import com.example.dailymonitoring.models.entities.EmailTemplateEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateConverter implements Converter<EmailTemplateData, EmailTemplateEntity> {

  @Override
  public EmailTemplateEntity convert(EmailTemplateData source) {
    return EmailTemplateEntity
        .builder()
        .name(source.getName())
        .description(source.getDescription())
        .template(source.getTemplate())
        .build();
  }
}
