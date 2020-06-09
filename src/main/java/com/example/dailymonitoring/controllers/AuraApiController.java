package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.AuraApi;
import com.example.dailymonitoring.models.AuraData;
import com.example.dailymonitoring.services.AuraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @author Sirghi Mihail
 */
@RestController
public class AuraApiController implements AuraApi {

  private final AuraService auraService;

  public AuraApiController(AuraService auraService) {
    this.auraService = auraService;
  }

  @Override
  public ResponseEntity<AuraData> getUserAura(@Valid @Min(1) Long userId) {
    return ResponseEntity.ok(auraService.getUserAura(userId));
  }
}
