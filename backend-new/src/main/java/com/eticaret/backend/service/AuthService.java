package com.eticaret.backend.service;

import com.eticaret.backend.dto.request.LoginRequest;
import com.eticaret.backend.dto.request.RegisterRequest;
import com.eticaret.backend.dto.response.AuthResponse;
import com.eticaret.backend.model.User;
import com.eticaret.backend.repository.UserRepository;
import com.eticaret.backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Bu e-posta adresi zaten kayıtlı.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Eğer frontend'den rol gelmemişse ROLE_USER ata
        String incomingRole = request.getRole();
        String roleToSet = (incomingRole == null || incomingRole.isBlank()) ? "ROLE_USER" : incomingRole;
        user.setRole(roleToSet);

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse("Kayıt başarılı", token, savedUser.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Şifre hatalı.");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse("Giriş başarılı", token, user.getRole());
    }
}