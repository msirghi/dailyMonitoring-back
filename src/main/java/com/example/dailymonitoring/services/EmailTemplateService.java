package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.EmailTemplateData;
import java.util.List;

public interface EmailTemplateService {

  EmailTemplateData createTemplate(EmailTemplateData emailTemplateData);

  EmailTemplateData getTemplateById(Long templateId);

  List<EmailTemplateData> getTemplates();

  int deleteTemplateById(Long templateId);

  EmailTemplateData updateTemplate(Long templateId, EmailTemplateData emailTemplateData);
}
