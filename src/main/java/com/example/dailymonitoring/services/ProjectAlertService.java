package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.ProjectAlertData;

import java.util.List;

/**
 * @author Sirghi Mihail
 */
public interface ProjectAlertService {

  ProjectAlertData addProjectAlert(Long userId, Long projectId, ProjectAlertData projectAlertData);

  ProjectAlertData getProjectAlertById(Long userId, Long projectId, Long alertId);

  List<ProjectAlertData> getAllProjectAlert(Long userId, Long projectId);

  ProjectAlertData updateProjectAlertMessage(Long userId, Long projectId, Long alertId, ProjectAlertData projectAlertData);

  ProjectAlertData updateProjectAlert(Long userId, Long projectId, Long alertId, ProjectAlertData projectAlertData);

  int deleteProjectAlertById(Long userId, Long projectId, Long alertId);
}
