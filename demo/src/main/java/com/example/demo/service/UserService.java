package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    ResponseEntity<?> signIn(String email, String password);
    void signUp(User user);
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    void deleteUser(Integer id);
}
