package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.UserProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProjectEntity, Long> {

  @Query("SELECT up FROM UserProjectEntity up "
      + "WHERE up.user.id = :userId "
      + "AND up.project.deleted = false "
      + "ORDER BY up.orderNumber ASC")
  Optional<List<UserProjectEntity>> getProjectsByUser(
      @Param("userId") Long userId
  );

  @Query("SELECT up FROM UserProjectEntity up "
      + "WHERE up.user.id = :userId "
      + "AND up.project.deleted = false " +
      "AND (up.project.id = :firstProjectId OR up.project.id = :secondProjectId)")
  Optional<List<UserProjectEntity>> getTwoProjectById(
      @Param("userId") Long userId,
      @Param("firstProjectId") Long firstProjectId,
      @Param("secondProjectId") Long secondProjectId
  );

  @Query("SELECT up FROM UserProjectEntity up "
      + "WHERE up.user.id = :userId "
      + "AND up.project.id = :projectId")
  Optional<UserProjectEntity> getProjectByUserIdAndProjectId(
      @Param("userId") Long userId,
      @Param("projectId") Long projectId
  );

  @Modifying
  @Transactional
  @Query("DELETE FROM UserProjectEntity up "
      + "WHERE up.user.id = :userId "
      + "AND up.project.id = :projectId")
  int deleteUserFromProject(
      @Param("userId") Long userId,
      @Param("projectId") Long projectId
  );

  @Query("SELECT up FROM UserProjectEntity up "
      + "WHERE up.project.id = :projectId")
  Optional<List<UserProjectEntity>> getProjectUsers(
      @Param("projectId") Long projectId
  );
}
