package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.exceptions.BadRequestException;
import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.entities.ProjectEntity;
import com.example.dailymonitoring.models.entities.ProjectTaskEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import com.example.dailymonitoring.models.enums.TaskStatusType;
import com.example.dailymonitoring.models.statistics.ProjectTaskStatisticsData;
import com.example.dailymonitoring.respositories.ProjectRepository;
import com.example.dailymonitoring.respositories.ProjectTaskRepository;
import com.example.dailymonitoring.respositories.UserProjectRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.ProjectService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

  private final UserRepository userRepository;

  private final ProjectRepository projectRepository;

  private final UserProjectRepository userProjectRepository;

  private final ConversionService conversionService;

  private final ProjectTaskRepository projectTaskRepository;

  public ProjectServiceImpl(UserRepository userRepository,
                            ProjectRepository projectRepository,
                            UserProjectRepository userProjectRepository,
                            ConversionService conversionService,
                            ProjectTaskRepository projectTaskRepository) {
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
    this.userProjectRepository = userProjectRepository;
    this.conversionService = conversionService;
    this.projectTaskRepository = projectTaskRepository;
  }

  private UserEntity getUserById(Long userId) {
    return userRepository.getActiveUser(userId).orElseThrow(() ->
        new BadRequestException(Constants.NO_USER_FOUND));
  }

  @Override
  public ProjectData projectCreate(ProjectData projectData, Long userId) {
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
    return projectRepository
        .findById(projectId)
        .filter(project -> !project.getDeleted())
        .map(project -> conversionService.convert(project, ProjectData.class))
        .orElse(ProjectData.builder().build());
  }

  @Override
  public List<ProjectData> getProjectsByUser(Long userId) {
    return userProjectRepository
        .getProjectsByUser(userId)
        .map(list -> list
            .stream()
            .map(project -> conversionService.convert(project, ProjectData.class))
            .collect(Collectors.toList()))
        .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public ProjectData projectDelete(Long userId, Long projectId) {
    return projectRepository
        .getActiveProjectById(projectId)
        .map(project -> {
          projectRepository.markAsDeleted(projectId);
          return conversionService.convert(project, ProjectData.class);
        })
        .orElse(ProjectData.builder().build());
  }

  @Override
  @Transactional
  public ProjectData projectUpdate(Long userId, Long projectId, ProjectData projectData) {
    return projectRepository
        .getActiveProjectById(projectId)
        .map(project -> {
          project.setDescription(projectData.getDescription());
          project.setName(projectData.getName());
          projectData.setId(project.getId());
          return projectData;
        })
        .orElse(ProjectData.builder().build());
  }

  @Override
  @Transactional
  public ProjectData updateProjectName(Long userId, Long projectId, ProjectData projectData) {
    return projectRepository
        .getActiveProjectById(projectId)
        .map(projectEntity -> {
          projectEntity.setName(projectData.getName());
          projectData.setId(projectEntity.getId());
          return projectData;
        })
        .orElse(ProjectData.builder().build());
  }

  @Override
  public List<ProjectTaskStatisticsData> getTasksStatistics(Long userId, Long projectId) {
    projectRepository.getActiveProjectById(projectId).orElseThrow(ResourceNotFoundException::new);

    List<ProjectTaskEntity> allProjectTasks =
        projectTaskRepository.getAllTasksByProjectId(projectId).orElse(new ArrayList<>());

    List<UserProjectEntity> usersInProject =
        userProjectRepository.getProjectUsers(projectId).orElseThrow(BadRequestException::new);

    int allTasksCount = allProjectTasks.size();
    List<ProjectTaskStatisticsData> statisticsData = new ArrayList<>();

    usersInProject.forEach(userProjectEntity -> {
      List<ProjectTaskEntity> projectTaskEntities =
          allProjectTasks
              .stream()
              .filter(task ->
                  task.getStatus().equals(TaskStatusType.DONE)
                      && task.getTasksDoneBy() != null
                      && task.getTasksDoneBy().equals(userProjectEntity.getUser()))
              .collect(Collectors.toList());

      if (allTasksCount != 0) {
        statisticsData.add(ProjectTaskStatisticsData
            .builder()
            .fullName(userProjectEntity.getUser().getFullName())
            .userId(userProjectEntity.getUser().getId())
            .tasksDone((long) projectTaskEntities.size())
            .taskPercentage((long) ((projectTaskEntities.size() * 100) / allTasksCount))
            .build());
      }
    });

    return statisticsData;
  }
}
