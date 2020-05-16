package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.ProjectTaskApi;
import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.UserTaskData;
import com.example.dailymonitoring.services.ProjectTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class ProjectTaskController implements ProjectTaskApi {

  private final ProjectTaskService projectTaskService;

  public ProjectTaskController(
      ProjectTaskService projectTaskService) {
    this.projectTaskService = projectTaskService;
  }

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
    return result.getId() != null
        ? ResponseEntity.ok().body(result)
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<List<TaskData>> getAllTasksByProjectId(@Min(1) Long userId,
                                                               @Min(1) Long projectId) {
    return ResponseEntity.ok().body(projectTaskService.getAllTasksByProjectId(userId, projectId));
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
  public ResponseEntity<List<UserTaskData>> getAllInProgressProjectTasks(@Min(1) Long userId,
                                                                         @Min(1) Long projectId) {
    List<UserTaskData> list = projectTaskService.getAllInProgressProjectTasks(userId, projectId);
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
