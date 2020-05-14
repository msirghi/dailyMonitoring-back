package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.TaskApi;
import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.services.impl.TaskServiceImpl;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController implements TaskApi {

  private TaskServiceImpl taskServiceImpl;

  public TaskController(TaskServiceImpl taskServiceImpl) {
    this.taskServiceImpl = taskServiceImpl;
  }
  
  @Override
  public ResponseEntity<?> taskCreate(@Valid TaskData taskData, @Valid @Min(1) Long userId) {
    TaskData result = this.taskServiceImpl.createTask(taskData, userId);
    return result.getId() != null
        ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<List<TaskData>> taskGet(@Valid @Min(1) Long userId) {
    List<TaskData> result = this.taskServiceImpl.getTask(userId);
    return !result.isEmpty()
        ? ResponseEntity.ok().body(result)
        : ResponseEntity.notFound().build();
  }

  @Override  //Rename getTaskByID
  public ResponseEntity<?> particularTaskGet(@Valid @Min(1) Long userId,
      @Valid @Min(1) Long taskId) {
    TaskData result = this.taskServiceImpl.getParticularTask(userId, taskId);
    return result.getId() != null
        ? ResponseEntity.ok().body(result)
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<?> taskDelete(@Valid @Min(1) Long userId, @Valid @Min(1) Long taskId) {
    boolean result = this.taskServiceImpl.deleteTask(userId, taskId);
    return result
        ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<List<TaskData>> doneTasksGet(@Valid @Min(1) Long userId) {
    List<TaskData> result = this.taskServiceImpl.getDoneTasks(userId);
    return !result.isEmpty()
        ? ResponseEntity.ok().body(result)
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<List<TaskData>> inProgressTasksGet(@Valid @Min(1) Long userId) {
    List<TaskData> result = this.taskServiceImpl.getInProgressTasks(userId);
    return !result.isEmpty()
        ? ResponseEntity.ok().body(result)
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<List<TaskData>> undoneTasksGet(@Valid @Min(1) Long userId) {
    List<TaskData> result = this.taskServiceImpl.getUndoneTasks(userId);
    return !result.isEmpty()
        ? ResponseEntity.ok().body(result)
        : ResponseEntity.notFound().build();
  }
}
