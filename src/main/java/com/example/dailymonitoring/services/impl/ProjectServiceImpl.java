package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.entities.ProjectEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import com.example.dailymonitoring.respositories.ProjectRepository;
import com.example.dailymonitoring.respositories.UserProjectRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.ProjectService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

  private final UserRepository userRepository;

  private final ProjectRepository projectRepository;

  private final UserProjectRepository userProjectRepository;

  private final ConversionService conversionService;

  public ProjectServiceImpl(UserRepository userRepository,
      ProjectRepository projectRepository,
      UserProjectRepository userProjectRepository,
      ConversionService conversionService) {
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
    this.userProjectRepository = userProjectRepository;
    this.conversionService = conversionService;
  }

  private UserEntity getUserById(Long userId) {
    return userRepository.getActiveUser(userId).orElse(UserEntity.builder().build());
  }

//  private void validateUser(Long userId) {
//    if (!Constants.getCurrentUserId().equals(userId)) {
//      throw new ForbiddenException();
//    }
//  }

  @Override
  public ProjectData projectCreate(ProjectData projectData, Long userId) {
//    validateUser(userId);

    UserEntity user = getUserById(userId);
    ProjectEntity projectEntity = conversionService.convert(projectData, ProjectEntity.class);
    projectEntity.setDeleted(false);
    projectData.setId(projectRepository.save(projectEntity).getId());
    userProjectRepository
        .save(UserProjectEntity.builder().user(user).project(projectEntity).build());
    return projectData;
  }

  @Override
  public ProjectData getProjectById(Long userId, Long projectId) {
//    validateUser(userId);

    return projectRepository
        .findById(projectId)
        .filter(project -> !project.getDeleted())
        .map(project -> conversionService.convert(project, ProjectData.class))
        .orElse(ProjectData.builder().build());
  }

  @Override
  public List<ProjectData> getProjectsByUser(Long userId) {
//    validateUser(userId);

    return userProjectRepository
        .getProjectsByUser(userId)
        .stream()
        .map(project -> conversionService.convert(project, ProjectData.class))
        .collect(Collectors.toList());
  }

  @Override
  public ProjectData projectDelete(Long userId, Long projectId) {
//    validateUser(userId);

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
//    validateUser(userId);

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
