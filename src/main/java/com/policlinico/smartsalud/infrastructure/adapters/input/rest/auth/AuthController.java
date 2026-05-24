// infrastructure/adapters/input/rest/auth/AuthController.java
package com.policlinico.smartsalud.infrastructure.adapters.input.rest.auth;

import com.policlinico.smartsalud.application.ports.input.auth.AuthInputPort;
import com.policlinico.smartsalud.shared.dto.request.auth.*;
import com.policlinico.smartsalud.shared.dto.response.auth.AuthResponse;
import com.policlinico.smartsalud.shared.dto.response.auth.MeResponse;
import com.policlinico.smartsalud.shared.dto.response.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthInputPort authService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Logout exitoso")
                .build());
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
    
    @GetMapping("/me")
    public ResponseEntity<MeResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.getCurrentUser(userDetails.getUsername()));
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        authService.changePassword(request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Contraseña cambiada exitosamente")
                .build());
    }
}