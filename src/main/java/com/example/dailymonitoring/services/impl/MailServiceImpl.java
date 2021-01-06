package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.services.MailService;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

  private final JavaMailSender emailSender;

  public MailServiceImpl(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @SneakyThrows
  @Override
  public void sendMessage(String to, String subject, String text) {
    MimeMessage mimeMessage = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

    String htmlMsg;
    if (subject.equals(Constants.EMAIL_CONFIRMATION_SUBJECT)) {
      htmlMsg = "htmlThere";
    } else {
      htmlMsg = text;
    }
    helper.setText(htmlMsg, true);
    helper.setFrom("no-reply@dailymonitoring.com", "Daily Monitoring");
    helper.setTo(to);
    helper.setSubject(subject);
    emailSender.send(mimeMessage);
  }
}
