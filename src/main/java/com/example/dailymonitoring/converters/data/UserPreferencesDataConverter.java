package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.UserPreferencesData;
import com.example.dailymonitoring.models.entities.UserPreferencesEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Sirghi Mihail
 */
@Component
public class UserPreferencesDataConverter implements Converter<UserPreferencesEntity, UserPreferencesData> {

  @Override
  public UserPreferencesData convert(UserPreferencesEntity source) {
    return UserPreferencesData
        .builder()
        .id(source.getId())
        .dailyTaskCount(source.getDailyTaskCount())
        .build();
  }
}
