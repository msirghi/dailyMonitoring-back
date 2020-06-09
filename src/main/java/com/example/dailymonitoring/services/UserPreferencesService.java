package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.UserPreferencesData;

/**
 * @author Sirghi Mihail
 */
public interface UserPreferencesService {

  UserPreferencesData getUserPreferences(Long userId);

  UserPreferencesData updateDailyTaskCount(Long userId, UserPreferencesData data);
}
