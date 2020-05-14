package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.EmailTemplateApi;
import com.example.dailymonitoring.models.EmailTemplateData;
import com.example.dailymonitoring.services.EmailTemplateService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTemplateController implements EmailTemplateApi {

  private final EmailTemplateService emailTemplateService;

  public EmailTemplateController(
      EmailTemplateService emailTemplateService) {
    this.emailTemplateService = emailTemplateService;
  }

  @Override
  public ResponseEntity<EmailTemplateData> addTemplate(@Valid EmailTemplateData emailTemplateData) {
    EmailTemplateData result = emailTemplateService.createTemplate(emailTemplateData);
    return result.getId() == null
        ? ResponseEntity.badRequest().build()
        : ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Override
  public ResponseEntity<EmailTemplateData> getTemplateById(@Min(1) Long templateId) {
    EmailTemplateData result = emailTemplateService.getTemplateById(templateId);
    return result.getId() == null
        ? ResponseEntity.notFound().build()
        : ResponseEntity.ok().body(result);
  }

  @Override
  public ResponseEntity<List<EmailTemplateData>> getTemplates() {
    return ResponseEntity.ok().body(emailTemplateService.getTemplates());
  }

  @Override
  public ResponseEntity<Void> deleteTemplate(Long templateId) {
    return emailTemplateService.deleteTemplateById(templateId) == 0
        ? ResponseEntity.notFound().build()
        : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Override
  public ResponseEntity<EmailTemplateData> updateTemplate(@Min(1) Long templateId,
      @Valid EmailTemplateData emailTemplateData) {
    EmailTemplateData result = emailTemplateService.updateTemplate(templateId, emailTemplateData);
    return result.getId() == null
        ? ResponseEntity.notFound().build()
        : ResponseEntity.ok().body(result);
  }
}
