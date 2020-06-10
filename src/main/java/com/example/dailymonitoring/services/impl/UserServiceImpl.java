package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.exceptions.BadRequestException;
import com.example.dailymonitoring.exceptions.UserCreationException;
import com.example.dailymonitoring.models.EmailData;
import com.example.dailymonitoring.models.PasswordData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.UsernameData;
import com.example.dailymonitoring.models.entities.AuraEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.UserPreferencesEntity;
import com.example.dailymonitoring.models.enums.StatusType;
import com.example.dailymonitoring.respositories.AuraRepository;
import com.example.dailymonitoring.respositories.UserPreferencesRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.UserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final ConversionService conversionService;

  private final BCryptPasswordEncoder passwordEncoder;

  private final AuraRepository auraRepository;

  private final UserPreferencesRepository userPreferencesRepository;

  public UserServiceImpl(UserRepository userRepository,
                         ConversionService conversionService,
                         BCryptPasswordEncoder passwordEncoder,
                         AuraRepository auraRepository,
                         UserPreferencesRepository userPreferencesRepository) {
    this.userRepository = userRepository;
    this.conversionService = conversionService;
    this.passwordEncoder = passwordEncoder;
    this.auraRepository = auraRepository;
    this.userPreferencesRepository = userPreferencesRepository;
  }

  @Override
  public UserData createUser(UserData userData) {
    if (userRepository.getUserByUsername(userData.getUsername()).isPresent()) {
      throw new UserCreationException("Username is already taken.");
    } else if (userRepository.getUserByEmail(userData.getEmail()).isPresent()) {
      throw new UserCreationException("Email is already taken.");
    }

    userData.setPassword(passwordEncoder.encode(userData.getPassword()));

    UserEntity userEntity = conversionService.convert(userData, UserEntity.class);
    userEntity.setStatus(StatusType.ACTIVE);
    userEntity.setEnabled(false);
    userEntity.setDeleted(false);
    userData.setId(userRepository.save(userEntity).getId());
    userData.setStatus(userEntity.getStatus());
    userData.setPassword("");

    auraRepository.save(AuraEntity.builder().auraCount(0L).user(userEntity).build());
    userPreferencesRepository.save(UserPreferencesEntity
        .builder()
        .dailyTaskCount(Constants.DEFAULT_DAILY_GOAL)
        .user(userEntity)
        .build());
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
  @Transactional
  public UserData updateUser(Long userId, UserData userData) {
    return userRepository
        .getActiveUser(userId)
        .map(user -> {
          UserEntity userInDb = userRepository.getUserByEmail(userData.getEmail())
              .orElse(UserEntity.builder().build());

          if (userInDb.getId() == null) {
            user.setEmail(userData.getEmail());
            user.setFullName(userData.getFullName());
            userData.setId(userId);
            return userData;
          }
          throw new BadRequestException("Email is already taken");
        }).orElse(UserData.builder().build());
  }

  @Override
  @Transactional
  public boolean updateUserPasswordOnly(Long userId, PasswordData passwordData) {
    return userRepository
        .getActiveUser(userId)
        .map(user -> {
          if (!passwordEncoder.matches(passwordData.getOldpassword(), user.getPassword())) {
            return false;
          }
          passwordEncoder.matches(passwordData.getPassword(), user.getPassword());
          user.setPassword(passwordEncoder.encode(passwordData.getPassword()));
          return true;
        })
        .orElse(false);
  }

  @Override
  @Transactional
  public boolean updateUserEmailOnly(Long userId, EmailData emailData) {
    return userRepository
        .getActiveUser(userId)
        .map(user -> {
          if (userRepository.getUserByEmail(emailData.getEmail()).isPresent()) {
            return false;
          } else {
            user.setEmail(emailData.getEmail());
            return true;
          }
        }).orElse(false);
  }

  @Override
  @Transactional
  public boolean updateUserUsernameOnly(Long userId, UsernameData usernameData) {
    return userRepository
        .getActiveUser(userId)
        .map(user -> {
          if (userRepository.getUserByUsername(usernameData.getUsername()).isPresent()) {
            return false;
          } else {
            user.setUsername(usernameData.getUsername());
            return true;
          }
        }).orElse(false);
  }

  @Override
  public UserEntity getUserByUsername(String username) {
    return userRepository.getUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(""));
  }
}
