package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.statistics.StatisticsData;

public interface StatisticsService {

  StatisticsData getUsersCurrentYearStatistics();

  StatisticsData getUsersStatisticsForSelectedYear(int year);

  StatisticsData getTasksCurrentYearStatistics();

  StatisticsData getTasksStatisticsForSelectedYear(int year);

  StatisticsData getProjectsCurrentYearStatistics();

  StatisticsData getProjectsSelectedYearStatistics(int year);
}
