package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Sirghi Mihail
 */
@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

  @Query("SELECT notification FROM NotificationEntity notification "
      + "WHERE notification.user.id = :userId")
  Optional<List<NotificationEntity>> findAllByUserId(
      @Param("userId") Long userId
  );

  @Query("SELECT notification FROM NotificationEntity notification"
      + " WHERE notification.status = 'UNREAD' "
      + " AND notification.user.id = :userId "
      + " AND notification.id = :notificationId")
  Optional<NotificationEntity> findByNotificationAndUserId(
      @Param("userId") Long userId,
      @Param("notificationId") Long notificationId
  );

  @Transactional
  @Modifying
  @Query("UPDATE NotificationEntity notification "
      + "SET notification.status = 'READ' "
      + "WHERE notification.id = :id")
  int markAsReadById(
      @Param("id") Long id
  );

  @Transactional
  @Modifying
  @Query("UPDATE NotificationEntity notification "
      + "SET notification.status = 'READ' "
      + "WHERE notification.id IN (:ids) "
      + " AND notification.user.id = :userId")
  int markAsReadByList(
      @Param("userId") Long userId,
      @Param("ids") List<Long> ids
  );
}
