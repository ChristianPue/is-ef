package com.example.demo.security;

import java.util.Collections;

import org.apache.catalina.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = (User) userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

    return new org.springframework.security.core.userdetails.User(
      user.getUsername(),
      user.getPassword(),
      Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoles()))
    );
  }
}
