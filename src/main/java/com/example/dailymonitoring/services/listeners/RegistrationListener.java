package com.example.dailymonitoring.services.listeners;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.events.OnRegistrationCompleteEvent;
import com.example.dailymonitoring.services.AccountConfirmationService;
import com.example.dailymonitoring.services.MailService;
import java.util.UUID;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

  private final AccountConfirmationService accountConfirmationService;

  private final MailService mailService;

  public RegistrationListener(
      AccountConfirmationService accountConfirmationService,
      MailService mailService) {
    this.accountConfirmationService = accountConfirmationService;
    this.mailService = mailService;
  }

  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    this.confirmRegistration(event);
  }

  private void confirmRegistration(OnRegistrationCompleteEvent event) {
    UserData user = event.getUser();
    String token = UUID.randomUUID().toString();
    accountConfirmationService.createVerificationToken(user, token);

    String recipientAddress = user.getEmail();
    String subject = Constants.EMAIL_CONFIRMATION_SUBJECT;
    String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;

    mailService.sendMessage(recipientAddress, subject,
        "\r\n" + "http://localhost:8182" + confirmationUrl);
  }
}
