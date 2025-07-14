package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.SignupRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
  private final AuthService authService;

  @PostMapping("/signup")
  public void signup(SignupRequest request) {
    authService.signup(request);
  }

  @PostMapping("/login")
  public AuthResponse login(LoginRequest request) {
    return authService.login(request);
  }
}
