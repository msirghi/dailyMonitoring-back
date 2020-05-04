package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.entities.ProjectTaskEntity;
import com.example.dailymonitoring.models.entities.TaskEntity;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import com.example.dailymonitoring.models.enums.TaskStatusType;
import com.example.dailymonitoring.respositories.ProjectRepository;
import com.example.dailymonitoring.respositories.ProjectTaskRepository;
import com.example.dailymonitoring.respositories.TaskRepository;
import com.example.dailymonitoring.respositories.UserProjectRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.ProjectTaskService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private ConversionService conversionService;

  @Autowired
  private ProjectTaskRepository projectTaskRepository;

  @Autowired
  private UserProjectRepository userProjectRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @Override
  public TaskData projectTaskCreate(TaskData taskData, Long userId, Long projectId) {
    UserProjectEntity userProjectEntity =
        userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).orElse(null);
    if (userProjectEntity == null) {
      return TaskData.builder().build();
    }

    TaskEntity taskEntity = conversionService.convert(taskData, TaskEntity.class);
    if (taskEntity.getDate() != null) {
      taskEntity.setDate(taskData.getDates().get(0));
    }
    taskEntity.setStatus(TaskStatusType.INPROGRESS);
    taskEntity.setDeleted(false);
    taskEntity.setUser(userProjectEntity.getUser());

    return projectRepository
        .getActiveProjectById(projectId)
        .map(project -> {
          projectTaskRepository.save(ProjectTaskEntity.builder()
              .project(project)
              .task(taskEntity)
              .build());
          taskRepository.save(taskEntity);
          taskData.setId(taskEntity.getId());
          taskData.setStatus(TaskStatusType.INPROGRESS);
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
        .filter(
            projectTaskEntity -> projectTaskEntity.getTask().getStatus() != TaskStatusType.DELETED)
        .map(task -> conversionService.convert(task.getTask(), TaskData.class))
        .orElse(TaskData.builder().build());
  }

  @Override
  public List<TaskData> getAllTasksByProjectId(Long userId, Long projectId)
      throws NotFoundException {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      throw new NotFoundException("");
    }

    return projectTaskRepository.getTasksByProjectId(projectId)
        .map(tasks -> tasks
            .stream()
            .map(task -> {
              TaskData taskData = conversionService.convert(task.getTask(), TaskData.class);
              taskData.setDates(new ArrayList<LocalDateTime>() {{
                add(task.getTask().getDate());
              }});
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
          taskRepository.markAsDeleted(userId, taskId);
          return conversionService.convert(task.getTask(), TaskData.class);
        })
        .orElse(TaskData.builder().build());
  }

  @Override
  public TaskData updateProjectTask(TaskData taskData, Long userId, Long projectId,
      Long taskId) {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      return TaskData.builder().build();
    }
    return projectTaskRepository
        .findNotDeletedTaskById(taskId)
        .map(task -> {
          task.getTask().setName(taskData.getName());
          task.getTask().setDescription(taskData.getDescription());
          return conversionService.convert(taskRepository.save(task.getTask()), TaskData.class);
        })
        .orElse(TaskData.builder().build());
  }

  @Override
  public int markProjectTaskAsDone(Long userId, Long projectId, Long taskId) {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      return 0;
    }
    return taskRepository.markAsDone(userId, taskId);
  }

  @Override
  public List<TaskData> getAllInProgressProjectTasks(Long userId, Long projectId) {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      return null;
    }
    return projectTaskRepository
        .getAllInProgressProjectTasks(projectId)
        .map(taskEntities -> taskEntities
            .stream()
            .map(task -> {
              TaskData taskData = conversionService.convert(task.getTask(), TaskData.class);
              taskData.setDates(new ArrayList<LocalDateTime>() {{
                add(task.getTask().getDate());
              }});
              return taskData;
            })
            .collect(Collectors.toList())
        )
        .orElse(new ArrayList<>());
  }

  @Override
  public List<TaskData> getLastDoneTasks(Long userId, Long projectId) {
    if (!userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).isPresent()) {
      return null;
    }

    return projectTaskRepository
        .getLastDoneTasks(projectId, PageRequest.of(0, 5, Direction.DESC, "updatedAt"))
        .map(projectTaskEntities -> projectTaskEntities
            .stream()
            .map(projectTaskEntity -> conversionService.convert(projectTaskEntity.getTask(),
                TaskData.class))
            .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }
}
