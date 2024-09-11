package com.example.login_api.controllers;

import com.example.login_api.domain.user.User;
import com.example.login_api.dto.ChangePasswordDTO;
import com.example.login_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("Sucesso!");
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO body) {
        try {
            User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));

            if (passwordEncoder.matches(body.password(), user.getPassword())) {

                user.setPassword(passwordEncoder.encode(body.newPassword()));

                this.repository.save(user);

                return ResponseEntity.ok("Password changed successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials PAssword");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials Token");
        }
    }
}
