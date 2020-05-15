package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.ProjectApi;
import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.statistics.ProjectTaskStatisticsData;
import com.example.dailymonitoring.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class ProjectController implements ProjectApi {

  private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public ResponseEntity<ProjectData> projectCreate(@Valid ProjectData projectData,
                                                   @Min(1) Long userId) {
    ProjectData result = projectService.projectCreate(projectData, userId);
    return result.getId() != null
        ? ResponseEntity.status(HttpStatus.CREATED).body(result)
        : ResponseEntity.badRequest().build();
  }

  @Override
  public ResponseEntity<ProjectData> getProjectById(@Min(1) Long userId, @Min(1) Long projectId) {
    ProjectData result = projectService.getProjectById(userId, projectId);
    return result.getId() != null
        ? ResponseEntity.ok().body(result)
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<List<ProjectData>> getProjectsByUser(@Min(1) Long userId) {
    return ResponseEntity.ok().body(projectService.getProjectsByUser(userId));
  }

  @Override
  public ResponseEntity<ProjectData> projectDelete(@Min(1) Long userId, @Min(1) Long projectId) {
    return projectService.projectDelete(userId, projectId).getId() != null
        ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<ProjectData> projectUpdate(@Min(1) Long userId, @Min(1) Long projectId,
                                                   @Valid ProjectData projectData) {
    ProjectData result = projectService.projectUpdate(userId, projectId, projectData);
    return result.getId() != null
        ? ResponseEntity.ok(result)
        : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<ProjectData> updateProjectName(@Valid ProjectData projectData, @Min(1) Long userId, @Min(1) Long projectId) {
    ProjectData result = projectService.updateProjectName(userId, projectId, projectData);
    return result.getId() == null
        ? ResponseEntity.badRequest().build()
        : ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<List<ProjectTaskStatisticsData>> getTasksStatistics(@Min(1) Long userId, @Min(1) Long projectId) {
    return ResponseEntity.ok(projectService.getTasksStatistics(userId, projectId));
  }
}
