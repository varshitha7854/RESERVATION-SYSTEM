package com.example.reservations_app.dto;

import com.example.reservations_app.entity.AppUser;

public class AuthResponse extends UserResponse {

    private final String token;
    private final String tokenType = "Bearer";

    public AuthResponse(AppUser user, String token) {
        super(user);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}
