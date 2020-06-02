package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.ProjectUserData;

import java.util.List;

public interface ProjectUserService {

  ProjectUserData addProjectUser(ProjectUserData projectUserData);

  int deleteUserFromProject(Long projectId, Long userId);

  List<ProjectUserData> getAllProjectUsers(Long userId, Long projectId);

}
