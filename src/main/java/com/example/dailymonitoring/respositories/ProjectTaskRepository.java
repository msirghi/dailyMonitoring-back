package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.ProjectTaskEntity;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTaskEntity, Long> {

  @Query("SELECT pt FROM ProjectTaskEntity pt "
      + "WHERE pt.project.id = :projectId")
  Optional<List<ProjectTaskEntity>> getTasksByProjectId(
      @Param("projectId") Long projectId
  );

  @Transactional
  @Modifying
  @Query("UPDATE ProjectTaskEntity task "
      + "SET task.deleted = true "
      + "WHERE task.id = :taskId")
  void markAsDeleted(
      @Param("taskId") Long id
  );

  @Query("SELECT task FROM ProjectTaskEntity task "
      + "WHERE task.task.id = :taskId "
      + "AND task.task.deleted = false"
  )
  Optional<ProjectTaskEntity> findNotDeletedTaskById(
      @Param("taskId") Long id
  );

  @Query("SELECT pte FROM ProjectTaskEntity pte "
      + "WHERE pte.task.status = 'INPROGRESS' AND pte.project.id = :projectId")
  Optional<List<ProjectTaskEntity>> getAllInProgressProjectTasks(
      @Param("projectId") Long projectId
  );

  @Query("SELECT pte FROM ProjectTaskEntity pte "
      + "WHERE pte.task.status = 'DONE' AND pte.project.id = :projectId "
      + "ORDER BY pte.task.updatedAt desc")
  Optional<List<ProjectTaskEntity>> getLastDoneTasks(
      @Param("projectId") Long projectId, Pageable pageable
  );
}
