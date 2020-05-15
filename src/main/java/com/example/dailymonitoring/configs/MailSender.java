package com.example.dailymonitoring.configs;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSender {

  private final Environment env;

  public MailSender(Environment env) {
    this.env = env;
  }

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(env.getProperty("mail.host"));
    mailSender.setPort(587);

    mailSender.setUsername(env.getProperty("mail.username"));
    mailSender.setPassword(env.getProperty("mail.password"));

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
    props.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
    props.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
    props.put("mail.debug", env.getProperty("mail.debug"));
    props.put("mail.smtp.ssl.trust", env.getProperty("mail.smtp.ssl.trust"));

    return mailSender;
  }
}
