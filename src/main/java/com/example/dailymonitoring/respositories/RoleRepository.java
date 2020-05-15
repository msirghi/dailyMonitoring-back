package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.RoleEntity;
import com.example.dailymonitoring.models.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByName(@NotNull RoleType name);
}
