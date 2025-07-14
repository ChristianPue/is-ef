package com.example.demo.mapper;

import com.example.demo.dto.request.SignupRequest;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.Role;

public class UserMapper {
  public static User toEntity(SignupRequest request, String encodedPassword, Role role) {
    return User.builder()
      .username(request.username())
      .password(encodedPassword)
      .role(role)
      .build();
  }
}
