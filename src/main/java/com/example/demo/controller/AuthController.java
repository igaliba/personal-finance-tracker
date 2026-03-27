package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> found = authService.login(user.getEmail(), user.getPassword());

        if (found.isPresent()) {
            User u = found.get();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login Success");
            // On génère un faux token pour le localStorage
            response.put("token", "fake-jwt-token-" + u.getId());
            // On met les infos à la racine pour que Angular les trouve facilement
            response.put("id", u.getId());
            response.put("fullName", u.getFullName());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(Map.of("message", "Login Failed"));
    }
}