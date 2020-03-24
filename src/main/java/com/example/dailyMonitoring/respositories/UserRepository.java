package com.example.dailyMonitoring.respositories;

import com.example.dailyMonitoring.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  @Query("SELECT usr FROM UserEntity usr "
          + "WHERE usr.username = :username")
  Optional<UserEntity> getUserByUsername(
          @Param("username") String username
  );

  @Query("SELECT status FROM UserEntity usr " +
          "WHERE usr.id = :id")
  Optional<String> checkStatus(
          @Param("id") Long id
  );

  @Query("SELECT usr FROM UserEntity usr " +
          "WHERE usr.email = :email")
  Optional<UserEntity> getUserByEmail(
          @Param("email") String email
  );


  @Transactional
  @Modifying
  @Query("UPDATE UserEntity usr " +
         "SET usr.status = 'INACTIVE' " +
         "WHERE usr.id = :id")
  void markAsDeleted(
          @Param("id") Long id
  );
}
