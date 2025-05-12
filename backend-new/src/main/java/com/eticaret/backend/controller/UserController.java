package com.eticaret.backend.controller;

import com.eticaret.backend.model.User;
import com.eticaret.backend.repository.UserRepository;
import com.eticaret.backend.security.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public UserController(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public User getUserProfile(Authentication authentication) {
        String email = authentication.getName(); // JWT içindeki email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }
}