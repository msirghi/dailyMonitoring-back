package com.example.dailyMonitoring.services.impl;

import com.example.dailyMonitoring.models.EmailData;
import com.example.dailyMonitoring.models.PasswordData;
import com.example.dailyMonitoring.models.UserData;
import com.example.dailyMonitoring.models.UsernameData;
import com.example.dailyMonitoring.models.entities.UserEntity;
import com.example.dailyMonitoring.models.enums.StatusType;
import com.example.dailyMonitoring.respositories.UserRepository;
import com.example.dailyMonitoring.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final ConversionService conversionService;

  public UserServiceImpl(UserRepository userRepository,
                         ConversionService conversionService) {
    this.userRepository = userRepository;
    this.conversionService = conversionService;
  }

  @Override
  public UserData createUser(UserData userData) {
    if (userRepository.getUserByUsername(userData.getUsername()).isPresent()
            || userRepository.getUserByEmail(userData.getEmail()).isPresent()) {
      return UserData.builder().build();
    }

    UserEntity userEntity = conversionService.convert(userData, UserEntity.class);
    userEntity.setStatus(StatusType.ACTIVE);

    userData.setId(userRepository.save(userEntity).getId());
    userData.setStatus(userEntity.getStatus());
    userData.setPassword("");
    return userData;
  }

  @Override
  public UserData getUserById(Long userId) {
    return userRepository
            .findById(userId)
            .map(user -> conversionService.convert(user, UserData.class))
            .orElse(UserData.builder().build());
  }

  @Override
  public boolean deleteUser(Long userId) {
    return userRepository.checkStatus(userId)
            .map(status -> {
              if (StatusType.fromValue(status).equals(StatusType.INACTIVE)) {
                return false;
              }
              userRepository.markAsDeleted(userId);
              return true;
            }).orElse(false);
  }

  @Override
  public UserData updateUser(Long userId, UserData userData) {
    return userRepository
            .getActiveUser(userId)
            .map(user -> {
              List<UserEntity> userList = userRepository.getUserByUsernameOrEmail(userData.getUsername(),
                      userData.getEmail());
              if (userList.size() == 0) {
                user.setEmail(userData.getEmail());
                user.setFullName(userData.getFullName());
                user.setUsername(userData.getUsername());
                userData.setId(userId);
                return conversionService.convert(userRepository.save(user), UserData.class);
              }

              for (UserEntity userEntity : userList) {
                if (userEntity.getUsername().equalsIgnoreCase(userData.getUsername())) {
                  return UserData.builder().id(-2L).build();
                } else if (userEntity.getEmail().equalsIgnoreCase(userData.getEmail())) {
                  return UserData.builder().id(-1L).build();
                }
              }
              return UserData.builder().build();
            }).orElse(UserData.builder().build());
  }

  @Override
  public boolean updateUserPasswordOnly(Long userId, PasswordData passwordData) {
    return userRepository
            .getActiveUser(userId)
            .map(user -> {
              user.setPassword(passwordData.getPassword());
              userRepository.save(user);
              return true;
            })
            .orElse(false);
  }

  @Override
  public boolean updateUserEmailOnly(Long userId, EmailData emailData) {
    return userRepository
            .getActiveUser(userId)
            .map(user -> {
              if (userRepository.getUserByEmail(emailData.getEmail()).isPresent()) {
                return false;
              } else {
                user.setEmail(emailData.getEmail());
                userRepository.save(user);
                return true;
              }
            }).orElse(false);

  }

  @Override
  public boolean updateUserUsernameOnly(Long userId, UsernameData usernameData) {
    return userRepository
            .getActiveUser(userId)
            .map(user -> {
              if (userRepository.getUserByUsername(usernameData.getUsername()).isPresent()) {
                return false;
              } else {
                user.setEmail(usernameData.getUsername());
                userRepository.save(user);
                return true;
              }
            }).orElse(false);

  }
}


