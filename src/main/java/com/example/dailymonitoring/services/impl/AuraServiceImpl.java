package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import com.example.dailymonitoring.models.AuraData;
import com.example.dailymonitoring.models.entities.ProjectTaskEntity;
import com.example.dailymonitoring.respositories.AuraRepository;
import com.example.dailymonitoring.respositories.ProjectTaskRepository;
import com.example.dailymonitoring.respositories.TaskRepository;
import com.example.dailymonitoring.respositories.UserPreferencesRepository;
import com.example.dailymonitoring.services.AuraService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Sirghi Mihail
 */
@Service
public class AuraServiceImpl implements AuraService {

  private final ConversionService conversionService;

  private final AuraRepository auraRepository;

  private final TaskRepository taskRepository;

  private final ProjectTaskRepository projectTaskRepository;

  private UserPreferencesRepository userPreferencesRepository;

  private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  public AuraServiceImpl(ConversionService conversionService,
                         AuraRepository auraRepository,
                         TaskRepository taskRepository,
                         ProjectTaskRepository projectTaskRepository,
                         UserPreferencesRepository userPreferencesRepository) {
    this.conversionService = conversionService;
    this.auraRepository = auraRepository;
    this.taskRepository = taskRepository;
    this.projectTaskRepository = projectTaskRepository;
    this.userPreferencesRepository = userPreferencesRepository;
  }

  @Override
  public AuraData getUserAura(Long userId) {
    return auraRepository
        .getAuraByUser(userId)
        .map(auraEntity -> {
          AuraData data = conversionService.convert(auraEntity, AuraData.class);
          data.setTotalCompletedTasks(this.getTotalTasks(userId));
          data.setDailyGoal(this.getDailyGoal(userId));
          data.setTasksDone((long) this.getTodayTasks(userId).size());
          return data;
        }).orElseThrow(ResourceNotFoundException::new);
  }

  private Long getTotalTasks(Long userId) {
    return (long) getProjectTasks(userId);
  }

  private int getProjectTasks(Long userId) {
    return projectTaskRepository
        .getTotalDoneTasksByUser(userId)
        .orElse(new ArrayList<>())
        .size();
  }

  private Long getDailyGoal(Long userId) {
    return userPreferencesRepository
        .getUserPreferences(userId)
        .orElseThrow(ResourceNotFoundException::new)
        .getDailyTaskCount();
  }

  private List<ProjectTaskEntity> getTodayTasks(Long userId) {
    try {
      return projectTaskRepository
          .getTotalDoneTasksForToday(userId,
              dateFormat.parse(getSpecificDate("yesterday")),
              dateFormat.parse(getSpecificDate("tomorrow")))
          .orElse(new ArrayList<>());
    } catch (ParseException e) {
      return new ArrayList<>();
    }
  }

  private String getSpecificDate(String type) {
    Calendar calendar = Calendar.getInstance();

    if (type.equals("yesterday")) {
      calendar.add(Calendar.DATE, -1);
    } else {
      calendar.add(Calendar.DATE, 1);
    }
    return dateFormat.format(calendar.getTime());
  }
}
