package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.NotificationApi;
import com.example.dailymonitoring.models.NotificationData;
import com.example.dailymonitoring.models.NotificationListData;
import com.example.dailymonitoring.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Sirghi Mihail
 */
@RestController
public class NotificationController implements NotificationApi {

  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Override
  public ResponseEntity<List<NotificationData>> getNotificationsByUserId(@Min(1) Long userId) {
    return ResponseEntity.ok(notificationService.getUnreadNotificationByUserId(userId));
  }

  @Override
  public ResponseEntity<Void> markNotificationAsRead(@Min(1) Long userId, @Min(1) Long notificationId) {
    notificationService.markNotificationAsReadById(userId, notificationId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> markSomeNotificationAsRead(@Min(1) Long userId, @Valid NotificationListData listData) {
    notificationService.markNotificationAsRead(userId, listData);
    return ResponseEntity.noContent().build();
  }
}
