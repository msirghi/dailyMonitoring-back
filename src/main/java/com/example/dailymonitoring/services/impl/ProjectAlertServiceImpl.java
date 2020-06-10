package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import com.example.dailymonitoring.models.ProjectAlertData;
import com.example.dailymonitoring.models.entities.ProjectAlertEntity;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import com.example.dailymonitoring.respositories.ProjectAlertRepository;
import com.example.dailymonitoring.respositories.UserProjectRepository;
import com.example.dailymonitoring.services.MailService;
import com.example.dailymonitoring.services.ProjectAlertService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sirghi Mihail
 */

// TODO: get current logged user
@Service
public class ProjectAlertServiceImpl implements ProjectAlertService {

  private final ProjectAlertRepository projectAlertRepository;

  private final UserProjectRepository userProjectRepository;

  private final ConversionService conversionService;

  private final MailService mailService;

  public ProjectAlertServiceImpl(ProjectAlertRepository projectAlertRepository,
                                 UserProjectRepository userProjectRepository,
                                 ConversionService conversionService,
                                 MailService mailService) {
    this.projectAlertRepository = projectAlertRepository;
    this.userProjectRepository = userProjectRepository;
    this.conversionService = conversionService;
    this.mailService = mailService;
  }

  private UserProjectEntity getUserProjectById(Long userId, Long projectId) {
    return userProjectRepository.getProjectByNotDeletedUserIdAndProjectId(userId, projectId)
        .orElseThrow(() -> new ResourceNotFoundException("User or project does not exist."));
  }

  private void sendEmails(Long userId, Long projectId, UserProjectEntity initialUser, ProjectAlertData data) {
    userProjectRepository.getProjectUsers(projectId)
        .map(userProjectEntities -> {
          userProjectEntities.forEach(projectUser -> {
            if (!projectUser.getUser().getId().equals(userId)) {
              mailService.sendMessage(projectUser.getUser().getEmail(), Constants.NEW_PROJECT_ALERT,
                  String.format(Constants.NEW_PROJECT_ALERT_BODY,
                      initialUser.getProject().getName(),
                      initialUser.getUser().getFullName(),
                      data.getMessage(),
                      data.getType()));
            }
          });
          return userProjectEntities;
        });
  }

  @Override
  public ProjectAlertData addProjectAlert(Long userId, Long projectId, ProjectAlertData projectAlertData) {
    UserProjectEntity userProjectEntity = getUserProjectById(userId, projectId);

    ProjectAlertEntity alertEntity = conversionService.convert(projectAlertData, ProjectAlertEntity.class);
    alertEntity.setAuthor(userProjectEntity.getUser());
    alertEntity.setProject(userProjectEntity.getProject());
    alertEntity.setDeleted(false);

    if (projectAlertData.getAreMembersNotified() != null && projectAlertData.getAreMembersNotified()) {
      this.sendEmails(userId, projectId, userProjectEntity, projectAlertData);
    }

    if (projectAlertData.getDate() != null) {
      alertEntity.setDate(projectAlertData.getDate());
    }
    projectAlertRepository.save(alertEntity);
    projectAlertData.setId(alertEntity.getId());
    projectAlertData.setAuthorId(userProjectEntity.getUser().getId());
    projectAlertData.setAuthorName(userProjectEntity.getUser().getFullName());
    return projectAlertData;
  }

  @Override
  public ProjectAlertData getProjectAlertById(Long userId, Long projectId, Long alertId) {
    getUserProjectById(userId, projectId);
    return projectAlertRepository
        .getAlertById(alertId)
        .map(alert -> conversionService.convert(alert, ProjectAlertData.class))
        .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public List<ProjectAlertData> getAllProjectAlert(Long userId, Long projectId) {
    getUserProjectById(userId, projectId);
    return projectAlertRepository
        .getProjectAlertEntityByProjectAndDeletedFalseAndDateAfter(projectId)
        .map(projectAlertEntities -> projectAlertEntities
            .stream()
            .map(projectAlertEntity -> conversionService.convert(projectAlertEntity, ProjectAlertData.class))
            .collect(Collectors.toList()))
        .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  @Transactional
  public ProjectAlertData updateProjectAlertMessage(Long userId, Long projectId, Long alertId,
                                                    ProjectAlertData projectAlertData) {
    getUserProjectById(userId, projectId);
    return projectAlertRepository
        .getAlertById(alertId)
        .map(projectAlertEntity -> {
          projectAlertEntity.setMessage(projectAlertData.getMessage());
          return conversionService.convert(projectAlertEntity, ProjectAlertData.class);
        })
        .orElseThrow(ResourceNotFoundException::new);
  }

  // TODO: 05.06.2020 tests
  @Override
  @Transactional
  public ProjectAlertData updateProjectAlert(Long userId, Long projectId, Long alertId, ProjectAlertData projectAlertData) {
    UserProjectEntity userProjectEntity = getUserProjectById(userId, projectId);
    return projectAlertRepository
        .getAlertById(alertId)
        .map(projectAlertEntity -> {
          // TODO: 05.06.2020 rethink
          if (projectAlertData.getDate() != null) {
            projectAlertEntity.setDate(projectAlertData.getDate());
          }
          if (projectAlertData.getType() != null) {
            projectAlertEntity.setType(projectAlertData.getType());
          }
          projectAlertEntity.setMessage(projectAlertData.getMessage());

          if (projectAlertData.getAreMembersNotified() != null && projectAlertData.getAreMembersNotified()) {
            this.sendEmails(userId, projectId, userProjectEntity, projectAlertData);
          }

          return conversionService.convert(projectAlertEntity, ProjectAlertData.class);
        })
        .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  @Transactional
  public int deleteProjectAlertById(Long userId, Long projectId, Long alertId) {
    getUserProjectById(userId, projectId);

    return projectAlertRepository
        .getAlertById(alertId)
        .map(projectAlertEntity -> {
          projectAlertEntity.setDeleted(true);
          return 1;
        })
        .orElseThrow(ResourceNotFoundException::new);
  }
}
