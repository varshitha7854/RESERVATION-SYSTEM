package com.example.reservations_app.dto;

import com.example.reservations_app.entity.AppUser;

public class UserResponse {
    private Long id;
    private String name;
    private String email;

    public UserResponse(AppUser user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
