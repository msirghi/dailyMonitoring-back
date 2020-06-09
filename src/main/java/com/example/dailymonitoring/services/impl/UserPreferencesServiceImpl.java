package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import com.example.dailymonitoring.models.UserPreferencesData;
import com.example.dailymonitoring.respositories.UserPreferencesRepository;
import com.example.dailymonitoring.services.UserPreferencesService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Sirghi Mihail
 */
@Service
public class UserPreferencesServiceImpl implements UserPreferencesService {

  private final ConversionService conversionService;

  private final UserPreferencesRepository userPreferencesRepository;

  public UserPreferencesServiceImpl(ConversionService conversionService,
                                    UserPreferencesRepository userPreferencesRepository) {
    this.conversionService = conversionService;
    this.userPreferencesRepository = userPreferencesRepository;
  }

  @Override
  public UserPreferencesData getUserPreferences(Long userId) {
    return userPreferencesRepository
        .getUserPreferences(userId)
        .map(prefs -> conversionService.convert(prefs, UserPreferencesData.class))
        .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  @Transactional
  public UserPreferencesData updateDailyTaskCount(Long userId, UserPreferencesData data) {
    return userPreferencesRepository
        .getUserPreferences(userId)
        .map(prefs -> {
          prefs.setDailyTaskCount(data.getDailyTaskCount());
          data.setId(prefs.getId());
          return data;
        })
        .orElseThrow(ResourceNotFoundException::new);
  }
}
