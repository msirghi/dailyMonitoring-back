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
import com.example.dailymonitoring.models.statistics.StatisticsData;
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
    return userRepository.getActiveUser(userId)
        .orElseThrow(() -> new BadRequestException(Constants.NO_USER_FOUND));
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public ProjectData projectCreate(ProjectData projectData, Long userId) {
    UserEntity user = getUserById(userId);
    ProjectEntity projectEntity = conversionService.convert(projectData, ProjectEntity.class);
    projectEntity.setDeleted(false);
    projectData.setId(projectRepository.save(projectEntity).getId());
    userProjectRepository
        .save(UserProjectEntity
            .builder()
            .user(user)
            .orderNumber((long) this.getProjectsByUser(userId).size() + 1)
            .project(projectEntity)
            .build());
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
  @Transactional
  public void reorderProjects(Long userId, Long firstProjectId, Long secondProjectId) {
    if (firstProjectId.equals(secondProjectId)) {
      throw new BadRequestException(Constants.PROJECT_ID_NOT_EQUAL);
    }

    List<UserProjectEntity> userProjectList = userProjectRepository.getTwoProjectById(userId, firstProjectId, secondProjectId)
        .orElseThrow(BadRequestException::new);

    UserProjectEntity firstProject;
    UserProjectEntity secondProject;

    try {
      firstProject = userProjectList.get(0);
      secondProject = userProjectList.get(1);
    } catch (IndexOutOfBoundsException e) {
      throw new BadRequestException();
    }

    Long secondProjectOrder = secondProject.getOrderNumber();
    Long firstProjectOrder = firstProject.getOrderNumber();

    firstProject.setOrderNumber(secondProjectOrder);
    secondProject.setOrderNumber(firstProjectOrder);
  }

  @Override
  public List<ProjectData> getProjectsByUser(Long userId) {
    return userProjectRepository
        .getProjectsByUser(userId)
        .map(list -> list
            .stream()
            .map(project -> conversionService.convert(project, ProjectData.class))
            .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
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
  @Transactional
  public ProjectData updateProjectColor(Long userId, Long projectId, ProjectData projectData) {
    return userProjectRepository
        .getProjectByUserIdAndProjectId(userId, projectId)
        .map(projectEntity -> {
          projectEntity.getProject().setColor(projectData.getColor());
          projectData.setId(projectEntity.getProject().getId());
          return projectData;
        })
        .orElse(ProjectData.builder().build());
  }

  @Override
  public StatisticsData getTasksStatistics(Long userId, Long projectId) {
    userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).orElseThrow(ResourceNotFoundException::new);
    projectRepository.getActiveProjectById(projectId).orElseThrow(ResourceNotFoundException::new);

    List<ProjectTaskEntity> allProjectTasks =
        projectTaskRepository.getAllTasksByProjectId(projectId).orElse(new ArrayList<>());

    long allUndoneTasks = getUndoneTaskCount(allProjectTasks);
    int allTasksCount = allProjectTasks.size();

    List<ProjectTaskStatisticsData> statisticsData = new ArrayList<>();
    List<UserProjectEntity> usersInProject = userProjectRepository.getProjectUsers(projectId)
        .orElseThrow(BadRequestException::new);

    usersInProject.forEach(userProjectEntity -> {
      if (allTasksCount != 0) {
        statisticsData.add(
            getProjectTasksStatisticsObject(
                userProjectEntity, this.getListOfDoneTasks(allProjectTasks, userProjectEntity).size(), allTasksCount)
        );
      }
    });

    return StatisticsData
        .builder()
        .pieStatisticsData(statisticsData)
        .allProjectTaskCount((long) allTasksCount)
        .allUnDoneProjectTasks(allUndoneTasks)
        .allDoneProjectTasks(allTasksCount - allUndoneTasks)
        .build();
  }

  private List<ProjectTaskEntity> getListOfDoneTasks(List<ProjectTaskEntity> list, UserProjectEntity userProjectEntity) {
    return list
        .stream()
        .filter(task ->
            task.getStatus().equals(TaskStatusType.DONE)
                && task.getTaskDoneBy() != null
                && task.getTaskDoneBy().equals(userProjectEntity.getUser()))
        .collect(Collectors.toList());
  }

  private ProjectTaskStatisticsData getProjectTasksStatisticsObject(UserProjectEntity userProjectEntity, int tasksDone,
                                                                    int totalTasks) {
    return ProjectTaskStatisticsData
        .builder()
        .fullName(userProjectEntity.getUser().getFullName())
        .userId(userProjectEntity.getUser().getId())
        .tasksDone((long) tasksDone)
        .taskPercentage((long) ((tasksDone * 100) / totalTasks))
        .build();
  }

  private long getUndoneTaskCount(List<ProjectTaskEntity> list) {
    return list
        .stream()
        .filter(projectTaskEntity -> projectTaskEntity.getStatus().equals(TaskStatusType.INPROGRESS))
        .count();
  }
}
