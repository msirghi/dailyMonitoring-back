package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.TaskEntity;
import java.sql.Date;
import java.util.List;
import java.util.Map;
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

  @Transactional
  @Modifying
  @Query("UPDATE TaskEntity tsk "
      + "SET tsk.status = 'DONE' "
      + "WHERE tsk.id = :taskId")
  void markAsDone(
      @Param("taskId") Long taskId
  );

  @Query(value = "SELECT to_char(created_at, 'YYYY-MM'), count(id) FROM TASKS tsk "
      + "WHERE tsk.created_at >= :startDate AND tsk.created_at <= :endDate "
      + "GROUP BY TO_CHAR(created_at, 'YYYY-MM') ", nativeQuery = true)
  List<Map<Object, Object>> getTasksStatistics(
      @Param("startDate") Date startDate,
      @Param("endDate") Date endDate
  );

  @Query(value = "SELECT count(id) FROM TASKS tsk "
      + "WHERE TO_CHAR(tsk.created_at, 'YYYY') = :year", nativeQuery = true)
  int countTaskByYear(
      @Param("year") int year
  );
}