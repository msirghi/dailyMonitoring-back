package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.ProjectAlertApi;
import com.example.dailymonitoring.models.ProjectAlertData;
import com.example.dailymonitoring.services.ProjectAlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Sirghi Mihail
 */
@RestController
public class ProjectAlertController implements ProjectAlertApi {

  private final ProjectAlertService projectAlertService;

  public ProjectAlertController(ProjectAlertService projectAlertService) {
    this.projectAlertService = projectAlertService;
  }

  @Override
  public ResponseEntity<ProjectAlertData> addProjectAlert(@Valid ProjectAlertData projectAlertData,
                                                          @Min(1) Long userId,
                                                          @Min(1) Long projectId) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(projectAlertService.addProjectAlert(userId, projectId, projectAlertData));
  }

  @Override
  public ResponseEntity<ProjectAlertData> getProjectAlertById(@Min(1) Long userId,
                                                              @Min(1) Long projectId,
                                                              @Min(1) Long alertId) {
    return ResponseEntity.ok(projectAlertService.getProjectAlertById(userId, projectId, alertId));
  }

  @Override
  public ResponseEntity<List<ProjectAlertData>> getAllProjectAlerts(@Min(1) Long userId,
                                                                    @Min(1) Long projectId) {
    return ResponseEntity.ok(projectAlertService.getAllProjectAlert(userId, projectId));
  }

  @Override
  public ResponseEntity<ProjectAlertData> updateProjectAlertMessage(@Valid ProjectAlertData data,
                                                                    @Min(1) Long userId,
                                                                    @Min(1) Long projectId,
                                                                    @Min(1) Long alertId) {
    return ResponseEntity.ok(projectAlertService.updateProjectAlertMessage(userId, projectId, alertId, data));
  }

  @Override
  public ResponseEntity<ProjectAlertData> updateProjectAlert(@Valid ProjectAlertData projectAlertData,
                                                             @Min(1) Long userId,
                                                             @Min(1) Long projectId,
                                                             @Min(1) Long alertId) {
    return ResponseEntity.ok(projectAlertService.updateProjectAlert(userId, projectId, alertId, projectAlertData));
  }

  @Override
  public ResponseEntity<Void> deleteProjectAlert(@Min(1) Long userId,
                                                 @Min(1) Long projectId,
                                                 @Min(1) Long alertId) {
    projectAlertService.deleteProjectAlertById(userId, projectId, alertId);
    return ResponseEntity.noContent().build();
  }
}
