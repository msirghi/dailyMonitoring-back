package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.NotificationData;
import com.example.dailymonitoring.models.NotificationListData;

import java.util.List;

/**
 * @author Sirghi Mihail
 */
public interface NotificationService {

  List<NotificationData> getUnreadNotificationByUserId(Long userId);

  int markNotificationAsReadById(Long userId, Long notificationId);

  int markNotificationAsRead(Long userId, NotificationListData listData);

}
