package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.models.statistics.MonthsData;
import com.example.dailymonitoring.models.statistics.StatisticsData;
import com.example.dailymonitoring.respositories.ProjectRepository;
import com.example.dailymonitoring.respositories.TaskRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.StatisticsService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

  private List<Long> monthValues = new ArrayList<>();

  private UserRepository userRepository;

  private TaskRepository taskRepository;

  private ProjectRepository projectRepository;

  public StatisticsServiceImpl(
      UserRepository userRepository,
      TaskRepository taskRepository,
      ProjectRepository projectRepository) {
    this.userRepository = userRepository;
    this.taskRepository = taskRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  @Cacheable("usersStatisticsForCurrentYear")
  public StatisticsData getUsersCurrentYearStatistics() {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    return getYearStatistics(currentYear,
        (long) userRepository.countUsersByYear(currentYear), getUsers(currentYear));
  }

  @Override
  @Cacheable("usersStatisticsForSelectedYear")
  public StatisticsData getUsersStatisticsForSelectedYear(int year) {
    return getYearStatistics(year, (long) userRepository.countUsersByYear(year), getUsers(year));
  }

  @Override
  @Cacheable("tasksStatisticsForCurrentYear")
  public StatisticsData getTasksCurrentYearStatistics() {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    return getYearStatistics(currentYear, (long) taskRepository.countTaskByYear(currentYear),
        getTasks(currentYear));
  }

  @Override
  @Cacheable("tasksStatisticsForSelectedYear")
  public StatisticsData getTasksStatisticsForSelectedYear(int year) {
    return getYearStatistics(year, (long) taskRepository.countTaskByYear(year), getTasks(year));
  }

  @Override
  @Cacheable("projectsStatisticsForCurrentYear")
  public StatisticsData getProjectsCurrentYearStatistics() {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    return getYearStatistics(currentYear,
        (long) projectRepository.countProjectsByYear(currentYear), getProjects(currentYear));
  }

  @Override
  @Cacheable("projectsStatisticsForSelectedYear")
  public StatisticsData getProjectsSelectedYearStatistics(int year) {
    return getYearStatistics(year,
        (long) projectRepository.countProjectsByYear(year), getProjects(year));
  }

  private List<Map<Object, Object>> getProjects(int selectedYear) {
    return projectRepository.getProjectsStatistics(
        Date.valueOf(selectedYear + "-01-01"),
        Date.valueOf(selectedYear + "-12-31"));
  }

  private List<Map<Object, Object>> getTasks(int selectedYear) {
    return taskRepository.getTasksStatistics(
        Date.valueOf(selectedYear + "-01-01"),
        Date.valueOf(selectedYear + "-12-31"));
  }

  private List<Map<Object, Object>> getUsers(int selectedYear) {
    return userRepository.getUserStatistics(
        Date.valueOf(selectedYear + "-01-01"),
        Date.valueOf(selectedYear + "-12-31"));
  }

  private StatisticsData getYearStatistics(int selectedYear, Long total,
                                           List<Map<Object, Object>> mapToParse) {

    Map<String, String> finalMap = new HashMap<>();

    mapToParse.forEach(monthMap -> {
      String key = null;
      String val = null;
      for (Map.Entry<Object, Object> entry : monthMap.entrySet()) {
        String value = entry.getValue().toString();

        if (value.length() == 7 && value.contains("-")) {
          key = value.substring(5);
        } else {
          val = value;
        }

        if (key != null && val != null) {
          finalMap.put(key, val);
          key = null;
          val = null;
        }
      }
    });

    return StatisticsData
        .builder()
        .total(total)
        .year((long) selectedYear)
        .perMonth(mapMoths(finalMap))
        .progression(calculateProgression())
        .build();
  }

  private Float calculateProgression() {
    Float progression;
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    Long currentMonthValue = this.monthValues.get(currentMonth);
    Long lastMonthValue = this.monthValues.get(currentMonth - 1);

    try {
      if (lastMonthValue < currentMonthValue) {
        progression = ((currentMonthValue - lastMonthValue) / lastMonthValue) * 100F;
      } else {
        progression = ((lastMonthValue - currentMonthValue) / lastMonthValue) * 100F;
      }
    } catch (ArithmeticException e) {
      return 0F;
    }
    return progression;
  }

  private MonthsData mapMoths(Map<String, String> map) {
    monthValues = new ArrayList<>();
    for (int i = 1; i < 13; i++) {
      monthValues.add(0L);
    }

    MonthsData monthsData = new MonthsData();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      String value = entry.getKey();
      Long longValue = Long.valueOf(entry.getValue());
      switch (value) {
        case "01":
          monthValues.add(0, longValue);
          monthsData.setJanuary(longValue);
          break;
        case "02":
          monthValues.add(1, longValue);
          monthsData.setFebruary(longValue);
          break;
        case "03":
          monthValues.add(2, longValue);
          monthsData.setMarch(longValue);
          break;
        case "04":
          monthValues.add(3, longValue);
          monthsData.setApril(longValue);
          break;
        case "05":
          monthValues.add(4, longValue);
          monthsData.setMay(longValue);
          break;
        case "06":
          monthValues.add(5, longValue);
          monthsData.setJune(longValue);
          break;
        case "07":
          monthValues.add(6, longValue);
          monthsData.setJuly(longValue);
          break;
        case "08":
          monthValues.add(7, longValue);
          monthsData.setAugust(longValue);
          break;
        case "09":
          monthValues.add(8, longValue);
          monthsData.setSeptember(longValue);
          break;
        case "10":
          monthValues.add(9, longValue);
          monthsData.setOctober(longValue);
          break;
        case "11":
          monthValues.add(10, longValue);
          monthsData.setNovember(longValue);
          break;
        case "12":
          monthValues.add(11, longValue);
          monthsData.setDecember(longValue);
          break;
      }
    }
    return monthsData;
  }
}
