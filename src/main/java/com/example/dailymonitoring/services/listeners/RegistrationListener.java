package com.example.dailymonitoring.services.listeners;

import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.events.OnRegistrationCompleteEvent;
import com.example.dailymonitoring.services.MailService;
import com.example.dailymonitoring.services.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

  @Autowired
  private UserService userService;

  @Autowired
  private MessageSource messages;

  @Autowired
  private MailService mailService;

  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    this.confirmRegistration(event);
  }

  private void confirmRegistration(OnRegistrationCompleteEvent event) {
    UserData user = event.getUser();
    String token = UUID.randomUUID().toString();
    userService.createVerificationToken(user, token);

    String recipientAddress = user.getEmail();
    String subject = "Registration Confirmation";
    String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;

    mailService.sendMessage(recipientAddress, subject,
        "\r\n" + "http://localhost:8182" + confirmationUrl);
  }
}
