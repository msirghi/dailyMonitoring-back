package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.ProjectUserData;
import java.util.List;
import javassist.NotFoundException;

public interface ProjectUserService {

  ProjectUserData addProjectUser(ProjectUserData projectUserData);

  int deleteUserFromProject(Long projectId, Long userId);

  List<ProjectUserData> getAllProjectUsers(Long userId, Long projectId) throws NotFoundException;
}
