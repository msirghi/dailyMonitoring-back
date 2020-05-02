package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.UserProjectEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProjectEntity, Long> {

  @Query("SELECT up FROM UserProjectEntity up "
      + "WHERE up.user.id = :userId")
  List<UserProjectEntity> getProjectsByUser(
      @Param("userId") Long userId
  );
}
