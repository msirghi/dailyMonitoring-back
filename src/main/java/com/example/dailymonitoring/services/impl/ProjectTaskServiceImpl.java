package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.exceptions.BadRequestException;
import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.UserTaskData;
import com.example.dailymonitoring.models.entities.ProjectTaskEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import com.example.dailymonitoring.models.enums.TaskStatusType;
import com.example.dailymonitoring.respositories.ProjectRepository;
import com.example.dailymonitoring.respositories.ProjectTaskRepository;
import com.example.dailymonitoring.respositories.UserProjectRepository;
import com.example.dailymonitoring.services.ProjectTaskService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

  private final ConversionService conversionService;

  private final ProjectTaskRepository projectTaskRepository;

  private final UserProjectRepository userProjectRepository;

  private final ProjectRepository projectRepository;

  public ProjectTaskServiceImpl(
      ConversionService conversionService,
      ProjectTaskRepository projectTaskRepository,
      UserProjectRepository userProjectRepository,
      ProjectRepository projectRepository) {
    this.conversionService = conversionService;
    this.projectTaskRepository = projectTaskRepository;
    this.userProjectRepository = userProjectRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public TaskData projectTaskCreate(TaskData taskData, Long userId, Long projectId) {
    UserProjectEntity userProjectEntity = userProjectRepository
        .getProjectByUserIdAndProjectId(userId, projectId)
        .orElseThrow(() -> new BadRequestException(Constants.NO_USER_WITH_SUCH_PROJECT));

    ProjectTaskEntity newTask = conversionService.convert(taskData, ProjectTaskEntity.class);

    if (newTask.getDate() != null) {
      newTask.setDate(taskData.getDates().get(0));
    }
    if (taskData.getAssignedToId() == null) {
      taskData.setAssignedToId(userProjectEntity.getUser().getId());
    }

    UserEntity assignedTo = userProjectRepository
        .getProjectByUserIdAndProjectId(taskData.getAssignedToId(), projectId)
        .orElseThrow(() -> new BadRequestException(Constants.NO_USER_WITH_SUCH_PROJECT))
        .getUser();
    newTask.setTaskAssignedTo(assignedTo);
    newTask.setStatus(TaskStatusType.INPROGRESS);
    newTask.setDeleted(false);
    newTask.setTaskCreator(userProjectEntity.getUser());
    newTask.setCategoryId(1L);

    return projectRepository
        .getActiveProjectById(projectId)
        .map(project -> {
          newTask.setProject(project);
          projectTaskRepository.save(newTask);

          taskData.setId(newTask.getId());
          taskData.setStatus(TaskStatusType.INPROGRESS);
          taskData.setAssignedToName(assignedTo.getFullName());
          return taskData;
        })
        .orElse(TaskData.builder().build());
  }

  @Override
  public TaskData getProjectTaskById(Long userId, Long projectId, Long taskId) {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      return TaskData.builder().build();
    }

    return projectTaskRepository
        .findById(taskId)
        .filter(projectTaskEntity -> projectTaskEntity.getStatus() != TaskStatusType.DELETED
            && projectTaskEntity.getDeleted() != null)
        .map(task -> conversionService.convert(task, TaskData.class))
        .orElse(TaskData.builder().build());
  }

  @Override
  public List<TaskData> getAllTasksByProjectId(Long userId, Long projectId) {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      throw new ResourceNotFoundException();
    }

    return projectTaskRepository.getTasksByProjectId(projectId)
        .map(tasks -> tasks
            .stream()
            .map(task -> {
              TaskData taskData = conversionService.convert(task, TaskData.class);
              taskData.setAssignedToName(task.getTaskAssignedTo().getFullName());
              taskData.setDates(new ArrayList<LocalDateTime>() {
                {
                  add(task.getDate());
                }
              });
              return taskData;
            })
            .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  @Override
  public TaskData deleteProjectTask(Long userId, Long projectId, Long taskId) {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      return TaskData.builder().build();
    }

    return projectTaskRepository.findNotDeletedTaskById(taskId)
        .map(task -> {
          projectTaskRepository.markAsDeleted(taskId);
          return conversionService.convert(task, TaskData.class);
        })
        .orElse(TaskData.builder().build());
  }

  @Override
  @Transactional
  public TaskData updateProjectTask(TaskData taskData, Long userId, Long projectId,
                                    Long taskId) {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      throw new ResourceNotFoundException();
    }

    return projectTaskRepository
        .findNotDeletedTaskById(taskId)
        .filter(projectTaskEntity -> projectTaskEntity.getStatus() != TaskStatusType.DELETED)
        .map(task -> {
          if (taskData.getAssignedToId() != null) {
            UserEntity user = userProjectRepository
                .getProjectByUserIdAndProjectId(taskData.getAssignedToId(), projectId)
                .orElseThrow(() -> new BadRequestException(Constants.NO_USER_WITH_SUCH_PROJECT))
                .getUser();

            task.setTaskAssignedTo(user);
            taskData.setAssignedToName(user.getFullName());
          }
          task.setName(taskData.getName());
          task.setDescription(taskData.getDescription());
          taskData.setId(task.getId());
          return taskData;
        })
        .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public int markProjectTaskAsDone(Long userId, Long projectId, Long taskId) {
    UserProjectEntity userProject = userProjectRepository.getProjectByUserIdAndProjectId(userId,
        projectId).orElseThrow(ResourceNotFoundException::new);

    projectTaskRepository.getUndoneTaskById(taskId).orElseThrow(ResourceNotFoundException::new);
    return projectTaskRepository.markAsDone(taskId, userProject.getUser());
  }

  @Override
  public List<UserTaskData> getAllInProgressProjectTasks(Long userId, Long projectId) {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      throw new ResourceNotFoundException();
    }
    List<UserTaskData> list = new ArrayList<>();

    projectTaskRepository
        .getAllInProgressProjectTasks(projectId)
        .map(taskEntities -> taskEntities
            .stream()
            .map(task -> {
              TaskData taskData = conversionService.convert(task, TaskData.class);
              taskData.setAssignedToName(task.getTaskAssignedTo().getFullName());
              taskData.setDates(new ArrayList<LocalDateTime>() {
                {
                  add(task.getDate());
                }
              });
              list.add(UserTaskData.builder()
                  .user(conversionService.convert(task.getTaskAssignedTo(), UserData.class))
                  .task(taskData)
                  .build());
              return taskData;
            })
            .collect(Collectors.toList()));
    return list;
  }

  @Override
  public List<TaskData> getLastDoneTasks(Long userId, Long projectId) {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      throw new ResourceNotFoundException();
    }

    return projectTaskRepository
        .getLastDoneTasks(projectId, PageRequest.of(0, 5, Sort.Direction.DESC, "updatedAt"))
        .map(projectTaskEntities -> projectTaskEntities
            .stream()
            .map(projectTaskEntity -> conversionService.convert(projectTaskEntity, TaskData.class))
            .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }
}
