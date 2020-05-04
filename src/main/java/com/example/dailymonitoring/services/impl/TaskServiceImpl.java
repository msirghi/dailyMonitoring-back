package com.example.dailymonitoring.services.impl;


import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.entities.TaskEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.enums.TaskStatusType;
import com.example.dailymonitoring.respositories.TaskRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.TaskService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final ConversionService conversionService;
  private final UserRepository userRepository;

  public TaskServiceImpl(TaskRepository taskRepository,
      ConversionService conversionService, UserRepository userRepository) {
    this.taskRepository = taskRepository;
    this.conversionService = conversionService;
    this.userRepository = userRepository;
  }

  @Override
  public TaskData createTask(TaskData taskData, Long userId) {

    if (!userRepository.existsById(userId)) {
      return TaskData.builder().build();
    }
    UserEntity user = userRepository.getOne(userId);

    for (LocalDateTime data : taskData.getDates()) {
      TaskEntity taskEntity = conversionService.convert(taskData, TaskEntity.class);
      assert taskEntity != null;
      taskEntity.setUser(user);
      taskEntity.setStatus(TaskStatusType.INPROGRESS);
      taskEntity.setDate(data);
      taskData.setId(taskRepository.save(taskEntity).getId());
      taskData.setStatus(taskEntity.getStatus());
    }
    return taskData;
  }

  @Override
  public List<TaskData> getTask(Long userId) {
    List<TaskData> tasks = new ArrayList<>();
    if (!userRepository.existsById(userId)) {
      return tasks;
    }
    List<TaskEntity> data = taskRepository.getTaskByUserId(userId);
    for (TaskEntity task : data
    ) {
      if (task.getStatus() == TaskStatusType.DELETED) {
        continue;
      }
      TaskData taskData = conversionService.convert(task, TaskData.class);
      List<LocalDateTime> dates = new ArrayList<>();
      dates.add(task.getDate());
      taskData.setDates(dates);
      tasks.add(taskData);
    }
    return tasks;
  }

  @Override
  public TaskData getParticularTask(Long userId, Long taskId) {
    return taskRepository
        .getTaskByUserIdAndTaskId(userId, taskId)
        .map(task -> {
          if (task.getId() == null || task.getUser().getId() == null
              || task.getStatus() == TaskStatusType.DELETED) {
            return TaskData.builder().build();
          }
          TaskData taskData = conversionService.convert(task, TaskData.class);
          List<LocalDateTime> dates = new ArrayList<>();
          dates.add(task.getDate());
          taskData.setDates(dates);
          return taskData;
        })
        .orElse(TaskData.builder().build());
  }

  @Override
  public boolean deleteTask(Long userId, Long taskId) {
    return taskRepository
        .getTaskByUserIdAndTaskId(userId, taskId)
        .map(task -> {
          if (task.getId() == null || task.getUser().getId() == null || task.getStatus()
              .equals(TaskStatusType.DELETED)) {
            return false;
          }
          taskRepository.markAsDeleted(userId, taskId);
          return true;
        })
        .orElse(false);
  }

  @Override
  public List<TaskData> getDoneTasks(Long userId) {
    List<TaskData> tasks = new ArrayList<>();
    if (!userRepository.existsById(userId)) {
      return tasks;
    }
    List<TaskEntity> data = taskRepository.getTaskByUserId(userId);
    for (TaskEntity task : data
    ) {
      if (task.getStatus() == TaskStatusType.DONE) {
        TaskData taskData = conversionService.convert(task, TaskData.class);
        List<LocalDateTime> dates = new ArrayList<>();
        dates.add(task.getDate());
        taskData.setDates(dates);
        tasks.add(taskData);
      }
    }
    return tasks;
  }

  @Override
  public List<TaskData> getInProgressTasks(Long userId) {
    List<TaskData> tasks = new ArrayList<>();
    if (!userRepository.existsById(userId)) {
      return tasks;
    }
    List<TaskEntity> data = taskRepository.getTaskByUserId(userId);
    for (TaskEntity task : data
    ) {
      if (task.getStatus() == TaskStatusType.INPROGRESS) {
        TaskData taskData = conversionService.convert(task, TaskData.class);
        List<LocalDateTime> dates = new ArrayList<>();
        dates.add(task.getDate());
        taskData.setDates(dates);
        tasks.add(taskData);
      }
    }
    return tasks;
  }

  @Override
  public List<TaskData> getUndoneTasks(Long userId) {
    List<TaskData> tasks = new ArrayList<>();
    if (!userRepository.existsById(userId)) {
      return tasks;
    }
    List<TaskEntity> data = taskRepository.getTaskByUserId(userId);
    for (TaskEntity task : data
    ) {
      if (task.getStatus() == TaskStatusType.UNDONE) {
        TaskData taskData = conversionService.convert(task, TaskData.class);
        List<LocalDateTime> dates = new ArrayList<>();
        dates.add(task.getDate());
        taskData.setDates(dates);
        tasks.add(taskData);
      }
    }
    return tasks;
  }
}

