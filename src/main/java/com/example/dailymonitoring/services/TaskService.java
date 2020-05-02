package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.entities.UserEntity;
import java.util.List;
import java.util.Optional;

public interface TaskService {
  TaskData createTask(TaskData taskDat, Long userId);

  List<TaskData> getTask(Long userId);

  TaskData getParticularTask(Long userId, Long taskId);

  boolean deleteTask(Long userId, Long taskId);

  List<TaskData> getDoneTasks(Long userId);

  List<TaskData> getInProgressTasks(Long userId);

  List<TaskData> getUndoneTasks(Long userId);
}
