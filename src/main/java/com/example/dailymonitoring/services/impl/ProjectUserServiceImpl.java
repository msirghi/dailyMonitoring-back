package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.exceptions.BadRequestException;
import com.example.dailymonitoring.models.ProjectUserData;
import com.example.dailymonitoring.models.entities.ProjectEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import com.example.dailymonitoring.models.events.OnAddAndDeleteProjectUserEvent;
import com.example.dailymonitoring.respositories.ProjectRepository;
import com.example.dailymonitoring.respositories.UserProjectRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.ProjectUserService;
import javassist.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectUserServiceImpl implements ProjectUserService {

  private final UserProjectRepository userProjectRepository;

  private final ProjectRepository projectRepository;

  private final UserRepository userRepository;

  private final Environment environment;

  private final ConversionService conversionService;

  private final ApplicationEventPublisher eventPublisher;

  public ProjectUserServiceImpl(
      UserProjectRepository userProjectRepository,
      ProjectRepository projectRepository,
      UserRepository userRepository,
      Environment environment,
      ConversionService conversionService,
      ApplicationEventPublisher eventPublisher) {
    this.userProjectRepository = userProjectRepository;
    this.projectRepository = projectRepository;
    this.userRepository = userRepository;
    this.environment = environment;
    this.conversionService = conversionService;
    this.eventPublisher = eventPublisher;
  }

  @Override
  public ProjectUserData addProjectUser(ProjectUserData projectUserData) {
    ProjectEntity project = projectRepository.getActiveProjectById(projectUserData.getProjectId())
        .orElseThrow(() -> new BadRequestException(Constants.NO_SUCH_PROJECT));

    UserEntity user = userRepository.getUserByEmail(projectUserData.getUserEmail())
        .orElseThrow(() -> new BadRequestException(Constants.USER_EMAIL_NOT_FOUND));

    UserProjectEntity userInProject =
        userProjectRepository.getProjectByUserIdAndProjectId(user.getId(), project.getId()).orElse(null);

    if (userInProject != null) {
      return ProjectUserData.builder().message(Constants.PROJECT_USER_ALREADY_EXISTS).build();
    }

    userProjectRepository.save(UserProjectEntity.builder().project(project).user(user).build());

    if (!environment.getProperty("app.env").equals("test")) {
      String emailSubject = String.format(Constants.PROJECT_USER_SUBJECT, project.getName());
      String emailBody = Constants.PROJECT_USER_ADD_BODY;

      eventPublisher.publishEvent(new OnAddAndDeleteProjectUserEvent(
          user.getEmail(),
          project.getName(),
          emailSubject,
          emailBody));
    }
    projectUserData.setMessage(Constants.EMAIL_SENT_SUCCESS);
    return projectUserData;
  }

  @Override
  public int deleteUserFromProject(Long projectId, Long userId) {
    UserProjectEntity userProjectEntity =
        userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).orElse(null);

    if (userProjectEntity == null) {
      return 0;
    }

    userProjectRepository.deleteUserFromProject(userId, projectId);
    if (!environment.getProperty("app.env").equals("test")) {
      String emailSubject = String.format(Constants.PROJECT_USER_DELETED_SUBJECT,
          userProjectEntity.getProject().getName());
      String emailBody = String.format(Constants.PROJECT_USER_DELETED_BODY,
          userProjectEntity.getProject().getName());

      eventPublisher.publishEvent(new OnAddAndDeleteProjectUserEvent(
          userProjectEntity.getUser().getEmail(),
          userProjectEntity.getProject().getName(),
          emailSubject,
          emailBody));
    }
    return 1;
  }

  @Override
  public List<ProjectUserData> getAllProjectUsers(Long userId, Long projectId)
      throws NotFoundException {
    UserProjectEntity userProjectEntity =
        userProjectRepository.getProjectByUserIdAndProjectId(userId, projectId).orElse(null);

    if (userProjectEntity == null) {
      throw new NotFoundException(Constants.PROJECT_NOT_FOUND);
    }

    return userProjectRepository.getProjectUsers(projectId)
        .map(userProjectEntities ->
            userProjectEntities
                .stream()
                .map(userProject -> conversionService.convert(userProject, ProjectUserData.class))
                .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }
}
