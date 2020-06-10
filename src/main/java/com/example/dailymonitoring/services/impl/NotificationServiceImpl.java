package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import com.example.dailymonitoring.models.NotificationData;
import com.example.dailymonitoring.models.NotificationListData;
import com.example.dailymonitoring.models.enums.NotificationStatus;
import com.example.dailymonitoring.respositories.NotificationRepository;
import com.example.dailymonitoring.services.NotificationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sirghi Mihail
 */
@Service
public class NotificationServiceImpl implements NotificationService {

  private final ConversionService conversionService;

  private final NotificationRepository notificationRepository;

  public NotificationServiceImpl(ConversionService conversionService,
                                 NotificationRepository notificationRepository) {
    this.conversionService = conversionService;
    this.notificationRepository = notificationRepository;
  }

  @Override
  public List<NotificationData> getUnreadNotificationByUserId(Long userId) {
    return notificationRepository
        .findAllByUserId(userId)
        .map(notifications -> notifications
            .stream()
            .map(notification -> conversionService.convert(notification, NotificationData.class))
            .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public int markNotificationAsReadById(Long userId, Long notificationId) {
    return notificationRepository
        .findByNotificationAndUserId(userId, notificationId)
        .map(notification -> {
          notification.setStatus(NotificationStatus.READ);
          return 1;
        })
        .orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public int markNotificationAsRead(Long userId, NotificationListData listData) {
    return notificationRepository.markAsReadByList(userId, listData.getNotificationIdList());
  }
}
