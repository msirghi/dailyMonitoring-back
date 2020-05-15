package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.models.RoleData;
import com.example.dailymonitoring.models.entities.RoleEntity;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.models.enums.RoleType;
import com.example.dailymonitoring.respositories.RoleRepository;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  public RoleServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public RoleData changeUserRole(RoleData roleData) {
    UserEntity userEntity = userRepository.getActiveUser(roleData.getUserId()).orElse(null);

    if (userEntity == null) {
      return RoleData.builder().build();
    }

    RoleEntity roleEntity = roleRepository.findByName(RoleType.fromValue(roleData.getName())).orElse(null);

    if (roleEntity == null) {
      return RoleData.builder().build();
    }

    userEntity.setRole(roleEntity);
    userRepository.save(userEntity);

    return roleData;
  }
}
