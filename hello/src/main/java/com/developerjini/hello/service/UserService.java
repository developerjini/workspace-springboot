package com.developerjini.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.developerjini.hello.model.UserEntity;
import com.developerjini.hello.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public UserEntity create(final UserEntity userEntity) {
    if (userEntity == null || userEntity.getUsername() == null) {
      throw new RuntimeException("Invalid argument");

    }
    final String username = userEntity.getUsername();
    if (userRepository.existsByUsername(username)) {
      log.warn("Username already exists {}", username);
      throw new RuntimeException("Username already exists");
    }

    return userRepository.save(userEntity);
  }

  // 로그인
  public UserEntity getByCredentials(final String username, final String password, PasswordEncoder encoder) {
    final UserEntity originalUser = userRepository.findByUsername(username);

    // matches
    // if (userEntity != null && encoder.matches(password,
    // userEntity.getPassword())) {
    // return userEntity;
    // }
    if (originalUser != null && encoder.matches(password, originalUser.getPassword())) {
      return originalUser;
    }

    return null;
    // return userRepository.findByUsernameAndPassword(username, password);
  }
}
