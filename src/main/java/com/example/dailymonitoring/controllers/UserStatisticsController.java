package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.UserStatisticsApi;
import com.example.dailymonitoring.models.statistics.ActivityStatisticsData;
import com.example.dailymonitoring.services.UserStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

/**
 * @author Sirghi Mihail
 */
@RestController
public class UserStatisticsController implements UserStatisticsApi {

  private final UserStatisticsService userStatisticsService;

  public UserStatisticsController(UserStatisticsService userStatisticsService) {
    this.userStatisticsService = userStatisticsService;
  }

  @Override
  public ResponseEntity<ActivityStatisticsData> getUsersStatistics(@Min(1) Long userId) {
    return ResponseEntity.ok(userStatisticsService.getUserStatistics(userId));
  }
}
