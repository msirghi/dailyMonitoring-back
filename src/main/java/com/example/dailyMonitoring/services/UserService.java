package com.example.dailyMonitoring.services;

import com.example.dailyMonitoring.models.EmailData;
import com.example.dailyMonitoring.models.UserData;
import com.example.dailyMonitoring.models.UsernameData;

public interface UserService {

  UserData createUser(UserData userData);

  UserData getUserById(Long userId);

  boolean deleteUser(Long userId);

  UserData updateUser(Long userId , UserData userData);

  boolean updateUserPasswordOnly(Long userId , UserData userData);

  boolean updateUserEmailOnly(Long userId , EmailData emailData);

  boolean updateUserUsernameOnly(Long userId , UsernameData usernameData);

}
