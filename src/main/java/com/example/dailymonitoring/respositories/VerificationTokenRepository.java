package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.entities.VerificationTokenEntity;
import java.sql.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Long> {

  @Query("SELECT token FROM VerificationTokenEntity token "
      + "WHERE token.token = :token")
  Optional<VerificationTokenEntity> getRowByToken(
      @Param("token") String token
  );

  VerificationTokenEntity findByToken(String token);

  VerificationTokenEntity findByUser(UserEntity user);

  @Modifying
  @Query("delete from VerificationTokenEntity t where t.expiryDate <= ?1")
  void deleteAllExpiredSince(Date now);
}
