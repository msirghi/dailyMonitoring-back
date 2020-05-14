package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.services.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

  private final JavaMailSender emailSender;

  public MailServiceImpl(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Override
  public void sendMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    emailSender.send(message);
  }
}
