package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.ProjectEntity;
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

  @Query(value = "SELECT to_char(created_at, 'YYYY-MM'), count(id) FROM PROJECTS project "
      + "WHERE project.created_at >= :startDate AND project.created_at <= :endDate "
      + "GROUP BY TO_CHAR(created_at, 'YYYY-MM') ", nativeQuery = true)
  List<Map<Object, Object>> getProjectsStatistics(
      @Param("startDate") Date startDate,
      @Param("endDate") Date endDate
  );

  @Query(value = "SELECT count(id) FROM PROJECTS project "
      + "WHERE TO_CHAR(project.created_at, 'YYYY') = :year", nativeQuery = true)
  int countProjectsByYear(
      @Param("year") int year
  );

}
