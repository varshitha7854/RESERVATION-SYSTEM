package com.example.reservations_app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    public PasswordService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hash(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public boolean verify(String plainPassword, String passwordHash) {
        if (plainPassword == null || passwordHash == null || passwordHash.trim().isEmpty()) {
            return false;
        }

        return passwordEncoder.matches(plainPassword, passwordHash);
    }

    public boolean isLegacyPlainPassword(String savedPassword) {
        return savedPassword != null && !savedPassword.startsWith("$2");
    }

    public boolean verifyLegacyPlainPassword(String plainPassword, String savedPassword) {
        return plainPassword != null && plainPassword.equals(savedPassword);
    }
}
