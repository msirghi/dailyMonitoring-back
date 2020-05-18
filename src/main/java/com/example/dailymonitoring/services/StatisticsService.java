package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.statistics.PieStatisticsData;

public interface StatisticsService {

  PieStatisticsData getUsersCurrentYearStatistics();

  PieStatisticsData getUsersStatisticsForSelectedYear(int year);

  PieStatisticsData getTasksCurrentYearStatistics();

  PieStatisticsData getTasksStatisticsForSelectedYear(int year);

  PieStatisticsData getProjectsCurrentYearStatistics();

  PieStatisticsData getProjectsSelectedYearStatistics(int year);
}
