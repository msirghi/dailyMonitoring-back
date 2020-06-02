package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.ProjectTaskEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTaskEntity, Long> {

  @Query("SELECT pt FROM ProjectTaskEntity pt "
      + "WHERE pt.project.id IN (:ids) "
      + "ORDER BY pt.updatedAt DESC")
  Optional<List<ProjectTaskEntity>> getProjectLastActivity(
      @Param("ids") List<Long> projectList,
      Pageable pageable
  );

  Optional<List<ProjectTaskEntity>> findAllByTaskCreatorId(
      @Param("userId") Long userId
  );

  @Query("SELECT pt FROM ProjectTaskEntity pt "
      + "WHERE pt.project.id = :projectId")
  Optional<List<ProjectTaskEntity>> getTasksByProjectId(
      @Param("projectId") Long projectId
  );

  @Transactional
  @Modifying
  @Query("UPDATE ProjectTaskEntity task "
      + "SET task.deleted = true, task.status = 'DELETED'"
      + "WHERE task.id = :taskId")
  void markAsDeleted(
      @Param("taskId") Long id
  );

  @Query("SELECT task FROM ProjectTaskEntity task "
      + "WHERE task.id = :taskId "
      + "AND task.deleted = false"
  )
  Optional<ProjectTaskEntity> findNotDeletedTaskById(
      @Param("taskId") Long id
  );

  @Query("SELECT pte FROM ProjectTaskEntity pte "
      + "WHERE pte.status = 'INPROGRESS' "
      + "AND pte.project.id = :projectId")
  Optional<List<ProjectTaskEntity>> getAllInProgressProjectTasks(
      @Param("projectId") Long projectId
  );

  @Query("SELECT pte FROM ProjectTaskEntity pte "
      + "WHERE pte.status = 'DONE'"
      + " AND pte.project.id = :projectId "
      + "ORDER BY pte.updatedAt desc")
  Optional<List<ProjectTaskEntity>> getLastDoneTasks(
      @Param("projectId") Long projectId, Pageable pageable
  );

  @Query("SELECT pte FROM ProjectTaskEntity pte "
      + "WHERE pte.project.id = :projectId")
  Optional<List<ProjectTaskEntity>> getAllTasksByProjectId(
      @Param("projectId") Long projectId
  );

  @Transactional
  @Modifying
  @Query("UPDATE ProjectTaskEntity tsk "
      + "SET tsk.taskDoneBy = :user, tsk.status = 'DONE' "
      + "WHERE tsk.id = :taskId")
  int markAsDone(
      @Param("taskId") Long taskId,
      @Param("user") UserEntity user
  );

  @Query("SELECT tsk FROM ProjectTaskEntity tsk" +
      " WHERE tsk.status is not 'DONE' AND tsk.id = :id")
  Optional<ProjectTaskEntity> getUndoneTaskById(
      @Param("id") Long taskId
  );
}
