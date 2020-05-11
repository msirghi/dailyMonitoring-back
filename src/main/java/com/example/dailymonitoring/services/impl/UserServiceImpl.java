package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.models.EmailData;
import com.example.dailymonitoring.models.PasswordData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.UsernameData;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.VerificationTokenEntity;
import com.example.dailymonitoring.models.enums.StatusType;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.respositories.VerificationTokenRepository;
import com.example.dailymonitoring.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ConversionService conversionService;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private VerificationTokenRepository verificationTokenRepository;

  @Override
  public UserData createUser(UserData userData) {
    if (userRepository.getUserByUsername(userData.getUsername()).isPresent()
        || userRepository.getUserByEmail(userData.getEmail()).isPresent()) {
      return UserData.builder().build();
    }
    userData.setPassword(passwordEncoder.encode(userData.getPassword()));

    UserEntity userEntity = conversionService.convert(userData, UserEntity.class);
    userEntity.setStatus(StatusType.ACTIVE);
    userEntity.setEnabled(false);
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
          List<UserEntity> userList =
              userRepository.getUserByUsernameOrEmail(userData.getUsername(),
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
          if (!passwordEncoder.matches(passwordData.getOldpassword(), user.getPassword())) {
            return false;
          }
          passwordEncoder.matches(passwordData.getPassword(), user.getPassword());
          user.setPassword(passwordEncoder.encode(passwordData.getPassword()));
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
            user.setUsername(usernameData.getUsername());
            userRepository.save(user);
            return true;
          }
        }).orElse(false);
  }

  @Override
  public void createVerificationToken(UserData user, String token) {
    UserEntity userEntity = conversionService.convert(user, UserEntity.class);
    userEntity.setId(user.getId());
    VerificationTokenEntity tokenEntity = new VerificationTokenEntity(token, userEntity);
    verificationTokenRepository.save(tokenEntity);
  }
}


