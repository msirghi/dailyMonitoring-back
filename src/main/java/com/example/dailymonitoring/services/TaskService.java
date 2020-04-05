package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.entities.UserEntity;

public interface TaskService {
  TaskData createTask(TaskData taskDat , UserEntity userId);
}
