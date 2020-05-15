package com.example.dailymonitoring.models.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class OnAddAndDeleteProjectUserEvent extends ApplicationEvent {

  private final String userEmail;
  private final String projectName;
  private final String subject;
  private final String body;

  public OnAddAndDeleteProjectUserEvent(String userEmail, String projectName, String subject, String body) {
    super(userEmail);

    this.userEmail = userEmail;
    this.projectName = projectName;
    this.subject = subject;
    this.body = body;
  }
}
