package com.example.dailymonitoring.models.events;

import com.example.dailymonitoring.models.UserData;
import java.util.Locale;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {

  private String appUrl;
  private Locale locale;
  private UserData user;

  public OnRegistrationCompleteEvent(UserData user, Locale locale, String appUrl) {
    super(user);

    this.user = user;
    this.locale = locale;
    this.appUrl = appUrl;
  }
}
