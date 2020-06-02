package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.controllers.api.ProjectUserApi;
import com.example.dailymonitoring.models.ProjectUserData;
import com.example.dailymonitoring.services.ProjectUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class ProjectUserController implements ProjectUserApi {

  private final ProjectUserService projectUserService;

  public ProjectUserController(
      ProjectUserService projectUserService) {
    this.projectUserService = projectUserService;
  }

  @Override
  public ResponseEntity<ProjectUserData> addProjectUser(@Valid ProjectUserData projectUserData) {
    ProjectUserData result = projectUserService.addProjectUser(projectUserData);

    return result.getMessage().equalsIgnoreCase(Constants.USER_EMAIL_NOT_FOUND) ||
        result.getMessage().equalsIgnoreCase(Constants.NO_SUCH_PROJECT) ||
        result.getMessage().equalsIgnoreCase(Constants.PROJECT_USER_ALREADY_EXISTS)
        ? ResponseEntity.badRequest().body(result)
        : ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Override
  public ResponseEntity<Void> deleteUserFromProject(Long projectId, Long userId) {
    return projectUserService.deleteUserFromProject(projectId, userId) == 0
        ? ResponseEntity.notFound().build()
        : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Override
  public ResponseEntity<List<ProjectUserData>> getAllProjectUsers(Long userId, Long projectId) {
    return ResponseEntity.ok(projectUserService.getAllProjectUsers(userId, projectId));
  }
}
