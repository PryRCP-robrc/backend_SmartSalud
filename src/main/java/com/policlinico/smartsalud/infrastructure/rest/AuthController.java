package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.dto.JwtResponse;
import com.policlinico.smartsalud.application.dto.LoginRequest;
import com.policlinico.smartsalud.application.dto.RegisterRequest;
import com.policlinico.smartsalud.application.dto.PerfilDTO;
import com.policlinico.smartsalud.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/dev-login")
    public ResponseEntity<JwtResponse> devLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.devLogin(request));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/me")
    public ResponseEntity<PerfilDTO> getMe(Authentication authentication) {
        return ResponseEntity.ok(authService.getMe(authentication.getName()));
    }
}
