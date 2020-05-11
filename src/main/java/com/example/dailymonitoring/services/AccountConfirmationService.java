package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.UserData;

public interface AccountConfirmationService {

  String confirmRegistration(String token);

  void createVerificationToken(UserData user, String token);
}
