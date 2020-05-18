package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.StatisticsApi;
import com.example.dailymonitoring.models.statistics.PieStatisticsData;
import com.example.dailymonitoring.services.StatisticsService;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController implements StatisticsApi {

  private final StatisticsService statisticsService;

  public StatisticsController(
      StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @Override
  public ResponseEntity<PieStatisticsData> getUsersStatistics(@Min(2020) Long selectedYear) {
    return selectedYear == null
        ? ResponseEntity.ok(statisticsService.getUsersCurrentYearStatistics())
        : ResponseEntity.ok(statisticsService.getUsersStatisticsForSelectedYear(
            Math.toIntExact(selectedYear)));
  }

  @Override
  public ResponseEntity<PieStatisticsData> getTasksStatistics(@Min(2020) Long selectedYear) {
    return selectedYear == null
        ? ResponseEntity.ok(statisticsService.getTasksCurrentYearStatistics())
        : ResponseEntity.ok(statisticsService.getTasksStatisticsForSelectedYear(
            Math.toIntExact(selectedYear)));
  }

  @Override
  public ResponseEntity<PieStatisticsData> getProjectsStatistics(@Min(2020) Long selectedYear) {
    return selectedYear == null
        ? ResponseEntity.ok(statisticsService.getProjectsCurrentYearStatistics())
        : ResponseEntity.ok(statisticsService.getProjectsSelectedYearStatistics(
            Math.toIntExact(selectedYear)));
  }
}
