package com.example.dailyMonitoring.respositories;

import com.example.dailyMonitoring.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  @Query("SELECT usr FROM UserEntity usr "
          + "WHERE usr.username = :username")
  Optional<UserEntity> getUserByUsername(
          @Param("username") String username
  );
}
