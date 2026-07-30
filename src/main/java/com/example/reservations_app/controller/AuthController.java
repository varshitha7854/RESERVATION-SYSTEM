package com.example.reservations_app.controller;

import com.example.reservations_app.dto.AuthRequest;
import com.example.reservations_app.dto.AuthResponse;
import com.example.reservations_app.entity.AppUser;
import com.example.reservations_app.security.JwtService;
import com.example.reservations_app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        AppUser user = userService.register(request);
        String token = authenticateAndCreateToken(user.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(user, token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AppUser user = userService.login(request);
        String token = authenticateAndCreateToken(user.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(user, token));
    }

    private String authenticateAndCreateToken(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        return jwtService.generateToken((UserDetails) authentication.getPrincipal());
    }
}
