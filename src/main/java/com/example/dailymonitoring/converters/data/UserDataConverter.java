package com.example.dailymonitoring.converters.data;

import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.entities.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDataConverter implements Converter<UserEntity, UserData> {

  @Override
  public UserData convert(UserEntity source) {
    return UserData
            .builder()
            .id(source.getId())
            .username(source.getUsername())
            .email(source.getEmail())
            .status(source.getStatus())
            .fullName(source.getFullName())
            .build();
  }
}
