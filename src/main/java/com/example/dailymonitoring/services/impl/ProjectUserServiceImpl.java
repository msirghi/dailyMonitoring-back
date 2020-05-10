package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.models.ProjectUserData;
import com.example.dailymonitoring.models.entities.ProjectEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import com.example.dailymonitoring.respositories.ProjectRepository;
import com.example.dailymonitoring.respositories.UserProjectRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.MailService;
import com.example.dailymonitoring.services.ProjectUserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ProjectUserServiceImpl implements ProjectUserService {

  @Autowired
  private UserProjectRepository userProjectRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MailService mailService;

  @Autowired
  private Environment environment;

  @Autowired
  private ConversionService conversionService;

  @Override
  public ProjectUserData addProjectUser(ProjectUserData projectUserData) {
    ProjectEntity project =
        projectRepository.getActiveProjectById(projectUserData.getProjectId()).orElse(null);

    if (project == null) {
      return ProjectUserData.builder().message(Constants.NO_SUCH_PROJECT).build();
    }

    UserEntity user = userRepository.getUserByEmail(projectUserData.getUserEmail()).orElse(null);

    if (user == null) {
      return ProjectUserData.builder().message(Constants.USER_EMAIL_NOT_FOUND).build();
    }

    UserProjectEntity userProjectEntity =
        userProjectRepository.getProjectByUserIdAndProjectId(user.getId(), project.getId())
            .orElse(null);

    if (userProjectEntity != null) {
      return ProjectUserData.builder().message(Constants.PROJECT_USER_ALREADY_EXISTS).build();
    }

    userProjectRepository.save(UserProjectEntity.builder().project(project).user(user).build());

    String emailSubject = String.format(Constants.PROJECT_USER_SUBJECT, project.getName());
    String emailBody = Constants.PROJECT_USER_ADD_BODY;

    mailService.sendMessage(environment.getProperty("mail.username"), emailSubject, emailBody);
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
    String emailSubject =
        String.format(Constants.PROJECT_USER_DELETED_SUBJECT,
            userProjectEntity.getProject().getName());
    String emailBody = String.format(Constants.PROJECT_USER_DELETED_BODY,
        userProjectEntity.getProject().getName());
    mailService.sendMessage(environment.getProperty("mail.username"), emailSubject, emailBody);
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
