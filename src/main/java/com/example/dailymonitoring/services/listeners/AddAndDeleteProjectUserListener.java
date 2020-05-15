package com.example.dailymonitoring.services.listeners;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.models.events.OnAddAndDeleteProjectUserEvent;
import com.example.dailymonitoring.services.MailService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AddAndDeleteProjectUserListener implements ApplicationListener<OnAddAndDeleteProjectUserEvent> {

  private final MailService mailService;

  public AddAndDeleteProjectUserListener(MailService mailService) {
    this.mailService = mailService;
  }

  @Override
  public void onApplicationEvent(OnAddAndDeleteProjectUserEvent event) {
    this.sendMailToNewProjectUser(event);
  }

  private void sendMailToNewProjectUser(OnAddAndDeleteProjectUserEvent event) {
    String emailSubject = String.format(Constants.PROJECT_USER_SUBJECT, event.getProjectName());
    String emailBody = Constants.PROJECT_USER_ADD_BODY;

    mailService.sendMessage(event.getUserEmail(), emailSubject, emailBody);

  }
}
