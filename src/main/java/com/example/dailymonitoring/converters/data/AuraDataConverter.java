package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.AuraData;
import com.example.dailymonitoring.models.entities.AuraEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Sirghi Mihail
 */
@Component
public class AuraDataConverter implements Converter<AuraEntity, AuraData> {

  @Override
  public AuraData convert(AuraEntity source) {
    return AuraData
        .builder()
        .id(source.getId())
        .auraCount(source.getAuraCount())
        .userId(source.getUser().getId())
        .build();
  }
}
