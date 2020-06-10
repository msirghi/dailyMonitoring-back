package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.ProjectAlertEntity;
import com.example.dailymonitoring.models.entities.ProjectEntity;
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
public interface ProjectAlertRepository extends JpaRepository<ProjectAlertEntity, Long> {

  @Query("SELECT alert "
      + "FROM ProjectAlertEntity alert "
      + "WHERE alert.id = :alertId "
      + "AND alert.author.deleted = false "
      + "AND alert.deleted = false")
  Optional<ProjectAlertEntity> getAlertById(
      @Param("alertId") Long alertId
  );

  @Query("SELECT alert "
      + "FROM ProjectAlertEntity alert "
      + "WHERE alert.author.deleted = false "
      + "AND alert.project.id = :projectId "
      + "AND alert.deleted = false "
      + "AND (alert.date > CURRENT_DATE OR alert.date = null) "
      + "ORDER BY alert.date DESC")
  Optional<List<ProjectAlertEntity>> getProjectAlertEntityByProjectAndDeletedFalseAndDateAfter(
      @Param("projectId") Long projectId
  );

  @Transactional
  @Modifying
  @Query("UPDATE ProjectAlertEntity alert "
      + "SET alert.deleted = true "
      + "WHERE alert.id = :id")
  int markAsDeleted(
      @Param("id") Long alertId
  );

}
