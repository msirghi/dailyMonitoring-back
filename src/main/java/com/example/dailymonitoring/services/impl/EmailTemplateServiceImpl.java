package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.models.EmailTemplateData;
import com.example.dailymonitoring.models.entities.EmailTemplateEntity;
import com.example.dailymonitoring.respositories.EmailTemplateRepository;
import com.example.dailymonitoring.services.EmailTemplateService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

  private final ConversionService conversionService;

  private final EmailTemplateRepository emailTemplateRepository;

  public EmailTemplateServiceImpl(
      ConversionService conversionService,
      EmailTemplateRepository emailTemplateRepository) {
    this.conversionService = conversionService;
    this.emailTemplateRepository = emailTemplateRepository;
  }

  @Override
  public EmailTemplateData createTemplate(EmailTemplateData emailTemplateData) {
    if (emailTemplateRepository.getTemplateByName(emailTemplateData.getName()).isPresent()) {
      return EmailTemplateData.builder().build();
    }

    EmailTemplateEntity newTemplate = conversionService.convert(emailTemplateData,
        EmailTemplateEntity.class);
    newTemplate.setDeleted(false);
    emailTemplateRepository.save(newTemplate);
    emailTemplateData.setId(newTemplate.getId());
    return emailTemplateData;
  }

  @Override
  public EmailTemplateData getTemplateById(Long templateId) {
    return emailTemplateRepository
        .getNotDeletedTemplateById(templateId)
        .map(template -> conversionService.convert(template, EmailTemplateData.class))
        .orElse(EmailTemplateData.builder().build());
  }

  @Override
  public List<EmailTemplateData> getTemplates() {
    return emailTemplateRepository
        .getNotDeletedTemplates()
        .stream()
        .map(template -> conversionService.convert(template, EmailTemplateData.class))
        .collect(Collectors.toList());
  }

  @Override
  public int deleteTemplateById(Long templateId) {
    if (!emailTemplateRepository.getNotDeletedTemplateById(templateId).isPresent()) {
      return 0;
    }
    emailTemplateRepository.markAsDeleted(templateId);
    return 1;
  }

  @Override
  @Transactional
  public EmailTemplateData updateTemplate(Long templateId, EmailTemplateData emailTemplateData) {
    return emailTemplateRepository
        .getNotDeletedTemplateById(templateId)
        .map(emailTemplateEntity -> {
          emailTemplateEntity.setDescription(emailTemplateData.getDescription());
          emailTemplateEntity.setName(emailTemplateData.getName());
          emailTemplateEntity.setTemplate(emailTemplateData.getTemplate());
          emailTemplateData.setId(emailTemplateEntity.getId());
          return emailTemplateData;
        })
        .orElse(EmailTemplateData.builder().build());
  }
}
