package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.entities.ProjectEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import com.example.dailymonitoring.respositories.ProjectRepository;
import com.example.dailymonitoring.respositories.UserProjectRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.ProjectService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private UserProjectRepository userProjectRepository;

  @Autowired
  private ConversionService conversionService;

  private UserEntity getUserById(Long userId) {
    return userRepository.getActiveUser(userId).orElse(UserEntity.builder().build());
  }

  @Override
  public ProjectData projectCreate(ProjectData projectData, Long userId) {
    UserEntity user = getUserById(userId);
    if (user.getId() == null) {
      return ProjectData.builder().build();
    }

    ProjectEntity projectEntity = conversionService.convert(projectData, ProjectEntity.class);
    projectData.setId(projectRepository.save(projectEntity).getId());
    userProjectRepository
        .save(UserProjectEntity.builder().user(user).project(projectEntity).build());
    return projectData;
  }

  @Override
  public ProjectData getProjectById(Long userId, Long projectId) {
    UserEntity user = getUserById(userId);
    if (user.getId() == null) {
      return ProjectData.builder().build();
    }

    return projectRepository
        .findById(projectId)
        .map(project -> conversionService.convert(project, ProjectData.class))
        .orElse(ProjectData.builder().build());
  }

  @Override
  public List<ProjectData> getProjectsByUser(Long userId) {
    UserEntity user = getUserById(userId);
    if (user.getId() == null) {
      return new ArrayList<>();
    }

    return userProjectRepository
        .getProjectsByUser(userId)
        .stream()
        .map(project -> conversionService.convert(project, ProjectData.class))
        .collect(Collectors.toList());
  }

  @Override
  public ProjectData projectDelete(Long userId, Long projectId) {
    UserEntity user = getUserById(userId);
    if (user.getId() == null) {
      return ProjectData.builder().build();
    }

    return projectRepository
        .getActiveProjectById(projectId)
        .map(project -> {
          projectRepository.markAsDeleted(projectId);
          return conversionService.convert(project, ProjectData.class);
        })
        .orElse(ProjectData.builder().build());
  }

  @Override
  public ProjectData projectUpdate(Long userId, Long projectId, ProjectData projectData) {
    UserEntity user = getUserById(userId);
    if (user.getId() == null) {
      return ProjectData.builder().build();
    }

    return projectRepository
        .getActiveProjectById(projectId)
        .map(project -> {
          project.setDescription(projectData.getDescription());
          project.setName(projectData.getName());
          return conversionService.convert(projectRepository.save(project), ProjectData.class);
        })
        .orElse(ProjectData.builder().build());
  }
}
