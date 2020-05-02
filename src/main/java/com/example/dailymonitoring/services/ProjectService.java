package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.ProjectData;
import java.util.List;

public interface ProjectService {

  ProjectData projectCreate(ProjectData projectData, Long userId);

  ProjectData getProjectById(Long userId, Long projectId);

  List<ProjectData> getProjectsByUser(Long userId);

  ProjectData projectDelete(Long userId, Long projectId);

  ProjectData projectUpdate(Long userId, Long projectId, ProjectData projectData);
}
