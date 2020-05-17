package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.VerificationTokenEntity;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.respositories.VerificationTokenRepository;
import com.example.dailymonitoring.services.AccountConfirmationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class AccountConfirmationServiceImpl implements AccountConfirmationService {

  private final VerificationTokenRepository verificationTokenRepository;

  private final UserRepository userRepository;

  private final ConversionService conversionService;

  public AccountConfirmationServiceImpl(
      VerificationTokenRepository verificationTokenRepository,
      UserRepository userRepository,
      ConversionService conversionService) {
    this.verificationTokenRepository = verificationTokenRepository;
    this.userRepository = userRepository;
    this.conversionService = conversionService;
  }

  @Override
  public String confirmRegistration(String token) {
    return validateVerificationToken(token);
  }

  @Override
  public void createVerificationToken(UserData user, String token) {
    UserEntity userEntity = conversionService.convert(user, UserEntity.class);
    userEntity.setId(user.getId());
    verificationTokenRepository.save(new VerificationTokenEntity(token, userEntity));
  }

  public String validateVerificationToken(String token) {
    final VerificationTokenEntity verificationToken = verificationTokenRepository
        .findByToken(token);

    if (verificationToken == null) {
      return Constants.TOKEN_INVALID;
    }

    final UserEntity user = verificationToken.getUser();
    final Calendar cal = Calendar.getInstance();
    if ((verificationToken.getExpiryDate()
        .getTime() - cal.getTime()
        .getTime()) <= 0) {
      verificationTokenRepository.delete(verificationToken);
      return Constants.TOKEN_EXPIRED;
    }

    user.setEnabled(true);
    userRepository.save(user);
    return Constants.TOKEN_VALID;
  }
}
