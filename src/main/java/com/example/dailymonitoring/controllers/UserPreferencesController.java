package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.UserPreferencesApi;
import com.example.dailymonitoring.models.UserPreferencesData;
import com.example.dailymonitoring.services.UserPreferencesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @author Sirghi Mihail
 */
@RestController
public class UserPreferencesController implements UserPreferencesApi {

  private final UserPreferencesService userPreferencesService;

  public UserPreferencesController(UserPreferencesService userPreferencesService) {
    this.userPreferencesService = userPreferencesService;
  }

  @Override
  public ResponseEntity<UserPreferencesData> getUserPreferences(@Valid @Min(1) Long userId) {
    return ResponseEntity.ok(userPreferencesService.getUserPreferences(userId));
  }

  @Override
  public ResponseEntity<UserPreferencesData> updateUserDailyGoal(@Valid UserPreferencesData data,
                                                                 @Valid @Min(1) Long userId) {
    return ResponseEntity.ok(userPreferencesService.updateDailyTaskCount(userId, data));
  }
}
