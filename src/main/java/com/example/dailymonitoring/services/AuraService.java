package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.AuraData;

/**
 * @author Sirghi Mihail
 */
public interface AuraService {

  AuraData getUserAura(Long userId);
}
