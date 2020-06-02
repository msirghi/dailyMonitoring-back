package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.statistics.ActivityStatisticsData;

/**
 * @author Sirghi Mihail
 */
public interface UserStatisticsService {

  ActivityStatisticsData getUserStatistics(Long userId);
}
