package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.StatisticsApi;
import com.example.dailymonitoring.models.statistics.StatisticsData;
import com.example.dailymonitoring.services.StatisticsService;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController implements StatisticsApi {

  @Autowired
  private StatisticsService statisticsService;

  @Override
  public ResponseEntity<StatisticsData> getUsersStatistics(@Min(2020) Long selectedYear) {
    return selectedYear == null
        ? ResponseEntity.ok(statisticsService.getUsersCurrentYearStatistics())
        : ResponseEntity.ok(statisticsService.getUsersStatisticsForSelectedYear(
            Math.toIntExact(selectedYear)));
  }

  @Override
  public ResponseEntity<StatisticsData> getTasksStatistics(@Min(2020) Long selectedYear) {
    return selectedYear == null
        ? ResponseEntity.ok(statisticsService.getTasksCurrentYearStatistics())
        : ResponseEntity.ok(statisticsService.getTasksStatisticsForSelectedYear(
            Math.toIntExact(selectedYear)));
  }
}
