package com.example.reservations_app.service;

import com.example.reservations_app.dto.AuthRequest;
import com.example.reservations_app.entity.AppUser;
import com.example.reservations_app.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public AppUser register(AuthRequest request) {
        String email = normalizeEmail(request.getEmail());
        if (isBlank(request.getName()) || isBlank(email) || isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Name, email, and password are required.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        AppUser user = new AppUser();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPassword(passwordService.hash(request.getPassword()));
        return userRepository.save(user);
    }

    public AppUser login(AuthRequest request) {
        String email = normalizeEmail(request.getEmail());
        if (isBlank(email) || isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Please enter both email and password.");
        }

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No account found with this email. Please register first."));

        if (passwordService.isLegacyPlainPassword(user.getPassword())
                && passwordService.verifyLegacyPlainPassword(request.getPassword(), user.getPassword())) {
            user.setPassword(passwordService.hash(request.getPassword()));
            return userRepository.save(user);
        }

        if (!passwordService.verify(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password does not match. Please try again.");
        }

        return user;
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
