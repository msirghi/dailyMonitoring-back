package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.exceptions.BadRequestException;
import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import com.example.dailymonitoring.models.AuraData;
import com.example.dailymonitoring.respositories.AuraRepository;
import com.example.dailymonitoring.services.AuraService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

/**
 * @author Sirghi Mihail
 */
@Service
public class AuraServiceImpl implements AuraService {

  private final ConversionService conversionService;

  private final AuraRepository auraRepository;

  public AuraServiceImpl(ConversionService conversionService,
                         AuraRepository auraRepository) {
    this.conversionService = conversionService;
    this.auraRepository = auraRepository;
  }

  @Override
  public AuraData getUserAura(Long userId) {
    return auraRepository
        .getAuraByUser(userId)
        .map(auraEntity -> conversionService.convert(auraEntity, AuraData.class))
        .orElseThrow(ResourceNotFoundException::new);
  }
}
