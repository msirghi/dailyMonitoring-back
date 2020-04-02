package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.UserEntity;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  @Query("SELECT usr FROM UserEntity usr "
      + "WHERE usr.username = :username")
  Optional<UserEntity> getUserByUsername(
      @Param("username") String username
  );

  @Query("SELECT status FROM UserEntity usr "
      + "WHERE usr.id = :id")
  Optional<String> checkStatus(
      @Param("id") Long id
  );

  @Query("SELECT usr FROM UserEntity usr "
      + "WHERE usr.email = :email")
  Optional<UserEntity> getUserByEmail(
      @Param("email") String email
  );

  @Transactional
  @Modifying
  @Query("UPDATE UserEntity usr "
      + "SET usr.status = 'INACTIVE' "
      + "WHERE usr.id = :id")
  void markAsDeleted(
      @Param("id") Long id
  );

  @Transactional
  @Modifying
  @Query("UPDATE UserEntity usr "
      + "SET usr.email = :email "
      + "WHERE usr.id = :id")
  void updateEmail(
      @Param("id") Long id,
      @Param("email") String email
  );

  @Transactional
  @Modifying
  @Query("UPDATE UserEntity usr "
      + "SET usr.username = :username "
      + "WHERE usr.id = :id")
  void updateUsername(
      @Param("id") Long id,
      @Param("username") String username
  );

  @Query("SELECT usr FROM UserEntity usr "
      + "WHERE usr.email = :email OR usr.username = :username")
  List<UserEntity> getUserByUsernameOrEmail(
      @Param("username") String username,
      @Param("email") String email
  );

  @Query("SELECT usr FROM UserEntity usr "
      + "WHERE usr.id = :id "
      + "AND usr.status = 'ACTIVE'")
  Optional<UserEntity> getActiveUser(
      @Param("id") Long id
  );

  @Query("SELECT usr FROM UserEntity usr "
      + "WHERE usr.username = :username")
  Optional<UserEntity> findUserByUsername(
      @Param("username") String username
  );
}
