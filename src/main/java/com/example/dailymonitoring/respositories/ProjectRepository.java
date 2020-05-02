package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.ProjectEntity;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

  @Transactional
  @Modifying
  @Query("UPDATE ProjectEntity project "
      + "SET project.deleted = true "
      + "WHERE project.id = :id")
  void markAsDeleted(
      @Param("id") Long id
  );

  @Query("SELECT project FROM ProjectEntity project "
      + "WHERE project.deleted = false "
      + "AND project.id = :projectId")
  Optional<ProjectEntity> getActiveProjectById(
    @Param("projectId") Long id
  );

}
