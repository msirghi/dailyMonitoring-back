package com.example.dailymonitoring.services.impl;


import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.entities.TaskEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.enums.StatusType;
import com.example.dailymonitoring.models.enums.TaskStatusType;
import com.example.dailymonitoring.respositories.TaskRepository;
import com.example.dailymonitoring.services.TaskService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.concurrent.Task;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final ConversionService conversionService;

  public TaskServiceImpl(TaskRepository taskRepository,
      ConversionService conversionService) {
    this.taskRepository = taskRepository;
    this.conversionService = conversionService;
  }

  @Override
  public TaskData createTask(TaskData taskData , UserEntity userId){
    if (userId == null) {
      return TaskData.builder().build();
    }

    for (LocalDateTime data:taskData.getDates()
    ) {
      TaskEntity taskEntity = conversionService.convert(taskData, TaskEntity.class);
      assert taskEntity != null;
      taskEntity.setUser(userId);
      taskEntity.setStatus(TaskStatusType.INPROGRESS);
      taskEntity.setDate(data);
      taskData.setId(taskRepository.save(taskEntity).getId());
      taskData.setStatus(taskEntity.getStatus());

    }
    return taskData; //Что ретурнить
  }

}
