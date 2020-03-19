package com.example.dailyMonitoring.converters;

import com.example.dailyMonitoring.models.UserData;
import com.example.dailyMonitoring.models.entities.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<UserData, UserEntity> {

  @Override
  public UserEntity convert(UserData source) {
    return UserEntity
            .builder()
            .fullName(source.getFullName())
            .email(source.getEmail())
            .username(source.getUsername())
            .password(source.getPassword())
            .build();
  }
}
