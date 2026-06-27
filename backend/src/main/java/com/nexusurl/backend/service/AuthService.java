package com.nexusurl.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nexusurl.backend.dto.AuthResponse;
import com.nexusurl.backend.dto.RegisterRequest;
import com.nexusurl.backend.entity.User;
import com.nexusurl.backend.enums.UserRole;
import com.nexusurl.backend.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .message("User registered successfully")
                .token("")
                .build();
    }
}