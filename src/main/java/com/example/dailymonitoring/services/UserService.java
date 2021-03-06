package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.EmailData;
import com.example.dailymonitoring.models.PasswordData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.UserProviderData;
import com.example.dailymonitoring.models.UsernameData;
import com.example.dailymonitoring.models.entities.UserEntity;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {

  UserData createUser(UserData userData);

  UserData getUserById(Long userId);

  boolean deleteUser(Long userId);

  UserData updateUser(Long userId, UserData userData);

  boolean updateUserPasswordOnly(Long userId, PasswordData passwordData);

  boolean updateUserEmailOnly(Long userId, EmailData emailData);

  boolean updateUserUsernameOnly(Long userId, UsernameData usernameData);

  UserEntity getUserByUsername(String username);

  UserData updateUserAvatar(Long userId, MultipartFile imageFile) throws Exception;

  UserProviderData createUserWithOtherProvider(UserProviderData data);

  UserProviderData getUserByIdToken(String idToken);

  void getUserByExternalId(String externalId);
}
