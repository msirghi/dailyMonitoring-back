package com.example.dailyMonitoring.services.impl;

import com.example.dailyMonitoring.models.UserData;
import com.example.dailyMonitoring.models.entities.UserEntity;
import com.example.dailyMonitoring.models.enums.StatusType;
import com.example.dailyMonitoring.respositories.UserRepository;
import com.example.dailyMonitoring.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

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
  public boolean updateUser(Long userId, UserData userData) {

    return userRepository.checkStatus(userId)
            .map(status -> {
              if (StatusType.fromValue(status).equals(StatusType.INACTIVE) || !userRepository.findById(userId).isPresent()) {
                return false;
              }
              if (!(getUserById(userId).getEmail().equals(userData.getEmail()))) {
                if (!(userRepository.getUserByEmail(userData.getEmail()).isPresent())) {
                  userRepository.updateEmail(userId, userData.getEmail());
                  return true;
                }
              }
              else if (!(getUserById(userId).getUsername().equals(userData.getUsername()))) {
                  if (!(userRepository.getUserByUsername(userData.getUsername()).isPresent())) {
                      userRepository.updateUsername(userId, userData.getUsername());
                      return true;
                  }
                }
//              if (!(getUserById(userId).getFullName().equals(userData.getFullName()))) {
//                  if (!(userRepository.getUserByFullName(userData.getFullName()).isPresent())) {
//                      userRepository.updateFullName(userId, userData.getFullName());
//                      return true;
//                  }
//               }
               else if(!(getUserById(userId).getPassword().equals(userData.getPassword()))) {
                   userRepository.updatePassword(userId , userData.getPassword());
                   return true;
                 }
              return false;
            }).orElse(false);
  }
}


