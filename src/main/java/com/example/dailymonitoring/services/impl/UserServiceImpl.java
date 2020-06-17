package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.exceptions.BadRequestException;
import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import com.example.dailymonitoring.exceptions.UserCreationException;
import com.example.dailymonitoring.models.EmailData;
import com.example.dailymonitoring.models.PasswordData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.UserProviderData;
import com.example.dailymonitoring.models.UsernameData;
import com.example.dailymonitoring.models.entities.AuraEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.UserPreferencesEntity;
import com.example.dailymonitoring.models.enums.StatusType;
import com.example.dailymonitoring.respositories.AuraRepository;
import com.example.dailymonitoring.respositories.UserPreferencesRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.UserService;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

  @SneakyThrows
  @Override
  public UserData createUser(UserData userData) {
    if (userRepository.getUserByUsername(userData.getUsername()).isPresent()) {
      throw new UserCreationException("Username is already taken.");
    } else if (userRepository.getUserByEmail(userData.getEmail()).isPresent()) {
      throw new UserCreationException("Email is already taken.");
    }

    userData.setPassword(passwordEncoder.encode(userData.getPassword()));

    UserEntity userEntity = conversionService.convert(userData, UserEntity.class);
    String imageName = this.createUserDefaultAvatar(userData);
    userEntity.setImagePath(imageName);
    userEntity.setStatus(StatusType.ACTIVE);
    userEntity.setEnabled(false);
    userEntity.setDeleted(false);
    userData.setId(userRepository.save(userEntity).getId());
    userData.setStatus(userEntity.getStatus());
    userData.setPassword("");
    userData.setImageName(imageName);

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

  @Override
  @Transactional(rollbackOn = Exception.class)
  public UserData updateUserAvatar(Long userId, MultipartFile imageFile) throws Exception {
    UserEntity userEntity = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
    byte[] bytes = imageFile.getBytes();
    String imageName = userEntity.getUsername();
    String imagePath = Constants.IMAGE_PATH + imageName + Constants.IMAGE_EXTENSION;

    this.deleteFileIfExists(imagePath);
    try {
      Path path = Paths.get(imagePath);
      Files.write(path, bytes);
    } catch (Exception e) {
      throw new BadRequestException();
    }
    return UserData.builder().build();
  }

  private String createUserDefaultAvatar(UserData data) throws Exception {
    final CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet request = new HttpGet(Constants.IMAGE_GENERATOR_URL + data.getUsername());
    HttpResponse response = httpClient.execute(request);
    HttpEntity entity = response.getEntity();
    String imageName = data.getUsername();
    String filePath = Constants.IMAGE_PATH + imageName + Constants.IMAGE_EXTENSION;

    if (entity != null) {
      this.deleteFileIfExists(filePath);
      BufferedInputStream bis = new BufferedInputStream(entity.getContent());
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
      int inByte;
      while ((inByte = bis.read()) != -1) bos.write(inByte);
      bis.close();
      bos.close();
    }
    return imageName;
  }

  private void deleteFileIfExists(String filePath) throws Exception {
//    Files.deleteIfExists(new File(filePath).toPath());
  }

  @Override
  public UserProviderData createUserWithOtherProvider(UserProviderData data) {
    try {
      if (userRepository.getUserByUsername(data.getUsername()).isPresent()) {
        throw new UserCreationException("Username is already taken.");
      } else if (userRepository.getUserByEmail(data.getEmail()).isPresent()) {
        throw new UserCreationException("Email is already taken.");
      }
      String imageName = this.createUserDefaultAvatar(UserData.builder()
          .fullName(data.getFullName())
          .username(data.getUsername()).build());

      UserEntity userEntity = UserEntity
          .builder()
          .email(data.getEmail())
          .username(data.getUsername())
          .provider(data.getProvider())
          .status(StatusType.ACTIVE)
          .externalId(data.getExternalId())
          .fullName(data.getFullName())
          .enabled(true)
          .imagePath(imageName)
          .build();
      userRepository.save(userEntity);

      data.setId(userEntity.getId());
      return data;
    } catch (Exception e) {
      throw new BadRequestException(e.getMessage());
    }
  }

  @Override
  public UserProviderData getUserByIdToken(String idToken) {
    return null;
  }

  @Override
  public void getUserByExternalId(String externalId) {
    if (!userRepository.getFirstByExternalId(externalId).isPresent()) {
      throw new ResourceNotFoundException();
    }
  }
}
