package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.RoleApi;
import com.example.dailymonitoring.models.RoleData;
import com.example.dailymonitoring.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RoleController implements RoleApi {

  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @Override
  public ResponseEntity<RoleData> changeUserRole(@Valid RoleData roleData) {
    RoleData result = roleService.changeUserRole(roleData);
    return result.getName() == null
        ? ResponseEntity.badRequest().build()
        : ResponseEntity.ok().build();
  }
}
