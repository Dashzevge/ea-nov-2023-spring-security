package com.example.demo.service.impl;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserService;
import com.example.demo.util.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo repository;
    private Jwt jwtUtil = new Jwt();

    @Override
    public void deleteUser(Integer id){
        repository.deleteById(id);
    }

    @Override
    public void signUp(User user) {
        repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public ResponseEntity<?> signIn(String email, String password){
        Optional<User> existingUser = this.findByEmail(email);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.getPassword().equals(password)) {
                String token = jwtUtil.generateToken(email, existingUser.get().getRole() != null ? existingUser.get().getRole().toString() : Role.USER.toString());
                return ResponseEntity.ok(token);
            } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }

}
