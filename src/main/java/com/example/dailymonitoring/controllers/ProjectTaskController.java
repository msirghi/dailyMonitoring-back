package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.ProjectTaskApi;
import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.services.ProjectTaskService;
import java.util.List;
import javassist.NotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectTaskController implements ProjectTaskApi {

  @Autowired
  private ProjectTaskService projectTaskService;

  @Override
  public ResponseEntity<TaskData> projectTaskCreate(@Valid TaskData projectData,
      @Min(1) Long userId, @Min(1) Long projectId) {
    TaskData result = projectTaskService.projectTaskCreate(projectData, userId, projectId);
    return result.getId() != null
        ? ResponseEntity.status(HttpStatus.CREATED).body(result)
        : ResponseEntity.badRequest().build();
  }

  @Override
  public ResponseEntity<TaskData> getProjectTaskById(@Min(1) Long userId, @Min(1) Long projectId,
      @Min(1) Long taskId) {
    TaskData result = projectTaskService.getProjectTaskById(userId, projectId, taskId);
    System.out.println(result);
    return result.getId() != null
        ? ResponseEntity.ok().body(result)
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<List<TaskData>> getAllTasksByProjectId(@Min(1) Long userId,
      @Min(1) Long projectId) {
    List<TaskData> result;
    try {
      result = projectTaskService.getAllTasksByProjectId(userId, projectId);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(result);
  }

  @Override
  public ResponseEntity<TaskData> deleteProjectTask(@Min(1) Long userId, @Min(1) Long projectId,
      @Min(1) Long taskId) {
    return projectTaskService.deleteProjectTask(userId, projectId, taskId).getId() != null
        ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<TaskData> updateProjectTask(@Valid TaskData taskData,
      @Min(1) Long userId, @Min(1) Long projectId, @Min(1) Long taskId) {
    TaskData result = projectTaskService.updateProjectTask(taskData, userId, projectId, taskId);
    return result.getId() != null
        ? ResponseEntity.ok().body(result)
        : ResponseEntity.badRequest().build();
  }

  @Override
  public ResponseEntity<TaskData> markProjectTaskAsDone(@Min(1) Long userId, @Min(1) Long projectId,
      @Min(1) Long taskId) {
    return projectTaskService.markProjectTaskAsDone(userId, projectId, taskId) == 0
        ? ResponseEntity.badRequest().build()
        : ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<List<TaskData>> getAllInProgressProjectTasks(@Min(1) Long userId,
      @Min(1) Long projectId) {
    List<TaskData> list = projectTaskService.getAllInProgressProjectTasks(userId, projectId);
    return list == null
        ? ResponseEntity.badRequest().build()
        : ResponseEntity.ok().body(list);
  }

  @Override
  public ResponseEntity<List<TaskData>> getLastDoneTasks(@Min(1) Long userId,
      @Min(1) Long projectId) {
    List<TaskData> list = projectTaskService.getLastDoneTasks(userId, projectId);
    return list == null
        ? ResponseEntity.badRequest().build()
        : ResponseEntity.ok().body(list);
  }
}
