package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.EmailData;
import com.example.dailymonitoring.models.PasswordData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.UsernameData;


public interface UserService {

  UserData createUser(UserData userData);

  UserData getUserById(Long userId);

  boolean deleteUser(Long userId);

  UserData updateUser(Long userId, UserData userData);

  boolean updateUserPasswordOnly(Long userId, PasswordData passwordData);

  boolean updateUserEmailOnly(Long userId, EmailData emailData);

  boolean updateUserUsernameOnly(Long userId, UsernameData usernameData);

  void createVerificationToken(UserData user, String token);

}
