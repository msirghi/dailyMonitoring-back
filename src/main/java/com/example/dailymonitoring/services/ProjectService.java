package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.statistics.ProjectTaskStatisticsData;
import com.example.dailymonitoring.models.statistics.StatisticsData;

import java.util.List;

public interface ProjectService {

  ProjectData projectCreate(ProjectData projectData, Long userId);

  ProjectData getProjectById(Long userId, Long projectId);

  List<ProjectData> getProjectsByUser(Long userId);

  ProjectData projectDelete(Long userId, Long projectId);

  ProjectData projectUpdate(Long userId, Long projectId, ProjectData projectData);

  ProjectData updateProjectName(Long userId, Long projectId, ProjectData projectData);

  ProjectData updateProjectColor(Long userId, Long projectId, ProjectData projectData);

  StatisticsData getTasksStatistics(Long userId, Long projectId);

  void reorderProjects(Long userId, Long firstProjectId, Long secondProjectId);
}
