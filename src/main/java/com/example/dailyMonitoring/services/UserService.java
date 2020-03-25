package com.example.dailyMonitoring.services;

import com.example.dailyMonitoring.models.UserData;

public interface UserService {

  UserData createUser(UserData userData);

  UserData getUserById(Long userId);

  boolean deleteUser(Long userId);

  boolean updateUser(Long userId , UserData userData);
}
