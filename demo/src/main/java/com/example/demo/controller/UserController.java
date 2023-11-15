package com.example.demo.controller;

import com.example.demo.entity.dto.UserDTO;
import com.example.demo.service.UserService;
import com.example.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @GetMapping("")
    public List<User> getUsers() {
        return service.findAll();
    }

    @GetMapping("/{userId}")
    public Optional<User> getUser(@PathVariable Integer userId) {
        return service.findById(userId);
    }

    @PostMapping("/signIn")
    public void loginUser(@RequestParam String email,@RequestParam String password) throws IllegalArgumentException {
        service.signIn(email, password);
    }

    @PostMapping("/signUp")
    public void addUser(@RequestBody User user) throws IllegalArgumentException {
         service.signUp(user);
    }

    @PostMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) throws IllegalArgumentException {
        service.deleteUser(userId);
    }
}
