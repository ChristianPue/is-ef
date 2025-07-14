package com.example.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.SignupRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.exception.DuplicatedResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authManager;
  private final UserDetailsService userDetailsService;

  // Crearse una cuenta
  public void signup(SignupRequest request) {
    if (userRepository.existsByUsername(request.username()) == true) {
      throw new DuplicatedResourceException("El nombre de usuario ya existe.");
    }

    User user = UserMapper.toEntity(request, passwordEncoder.encode(request.password()), Role.USER);
    
    userRepository.save(user);
  }

  // Ingresar a una cuenta
  public AuthResponse login(LoginRequest request) {
    authManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());

    User user = userRepository.findByUsername(userDetails.getUsername())
      .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe."));
    
    String token = JwtUtil.generateToken(user.getUsername(), user.getRole().name());

    return new AuthResponse(token);
  }
}
