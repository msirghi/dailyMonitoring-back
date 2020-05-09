package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.controllers.api.ProjectUserApi;
import com.example.dailymonitoring.models.ProjectUserData;
import com.example.dailymonitoring.services.ProjectUserService;
import java.util.List;
import javassist.NotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectUserController implements ProjectUserApi {

  @Autowired
  private ProjectUserService projectUserService;

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
    try {
      List<ProjectUserData> result = projectUserService.getAllProjectUsers(userId, projectId);
      return ResponseEntity.ok(result);
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
