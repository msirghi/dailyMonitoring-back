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
    if (userRepository.getUserByUsername(userData.getUsername()).isPresent()) {
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
}
