package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.AuraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sirghi Mihail
 */
@Repository
public interface AuraRepository extends JpaRepository<AuraEntity, Long> {

  @Query("SELECT aura from AuraEntity aura "
      + " WHERE aura.user.id = :userId "
      + " AND aura.user.status is not 'INACTIVE'")
  Optional<AuraEntity> getAuraByUser(
      @Param("userId") Long userId
  );
}
