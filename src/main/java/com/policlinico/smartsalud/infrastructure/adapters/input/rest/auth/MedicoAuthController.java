// infrastructure/adapters/input/rest/auth/MedicoAuthController.java
package com.policlinico.smartsalud.infrastructure.adapters.input.rest.auth;

import com.policlinico.smartsalud.application.ports.input.auth.MedicoAuthInputPort;
import com.policlinico.smartsalud.shared.dto.request.auth.LoginRequest;
import com.policlinico.smartsalud.shared.dto.response.auth.MedicoAuthResponse;
import com.policlinico.smartsalud.shared.dto.response.medico.MedicoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/medico")
@RequiredArgsConstructor
public class MedicoAuthController {

    private final MedicoAuthInputPort medicoAuthService;

    @PostMapping("/login")
    public ResponseEntity<MedicoAuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(medicoAuthService.login(request));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('MEDICO')")
    public ResponseEntity<MedicoResponse> me(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicoAuthService.me(userDetails.getUsername()));
    }
}
