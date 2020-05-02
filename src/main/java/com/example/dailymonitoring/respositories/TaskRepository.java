package com.example.dailymonitoring.respositories;


import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.entities.TaskEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

  @Query("SELECT tsk FROM TaskEntity tsk "
      + "WHERE tsk.user.id = :userId")
  List<TaskEntity> getTaskByUserId(
      @Param("userId") Long userId
  );

  @Query("SELECT tsk FROM TaskEntity tsk "
      + "WHERE tsk.user.id = :userId "
      + "AND tsk.id = :taskId")
  Optional<TaskEntity> getTaskByUserIdAndTaskId(
      @Param("userId") Long userId,
      @Param("taskId") Long taskId
  );

  @Transactional
  @Modifying
  @Query("UPDATE TaskEntity tsk "
      + "SET tsk.status = 'DELETED' "
      + "WHERE tsk.user.id = :userId "
      + "AND tsk.id = :taskId")
  void markAsDeleted(
      @Param("userId") Long userId,
      @Param("taskId") Long taskId
  );
}