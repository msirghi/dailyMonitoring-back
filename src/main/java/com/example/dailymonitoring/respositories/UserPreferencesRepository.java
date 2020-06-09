package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.UserPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sirghi Mihail
 */
@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferencesEntity, Long> {

  @Query("SELECT preferences from UserPreferencesEntity preferences "
      + " WHERE preferences.user.id = :userId "
      + " AND preferences.user.status is not 'INACTIVE'")
  Optional<UserPreferencesEntity> getUserPreferences(
      @Param("userId") Long userId
  );
}
