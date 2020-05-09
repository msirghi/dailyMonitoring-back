package com.example.dailymonitoring.services;

public interface MailService {

  void sendMessage(String to, String subject, String text);

}
