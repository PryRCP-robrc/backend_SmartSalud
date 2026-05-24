package com.smartsalud.backend.adapters.input.rest.auth;

import com.smartsalud.backend.adapters.input.rest.auth.dto.AuthResponse;
import com.smartsalud.backend.adapters.input.rest.auth.dto.LoginRequest;
import com.smartsalud.backend.adapters.input.rest.auth.dto.RegisterRequest;
import com.smartsalud.backend.domain.model.usuario.Usuario;
import com.smartsalud.backend.domain.port.in.auth.AuthUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = authUseCase.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody RegisterRequest request) {
        Usuario usuario = new Usuario(
                null,
                request.getEmail(),
                request.getPassword(),
                request.getRol(),
                request.getNombre(),
                request.getApellido()
        );
        Usuario savedUsuario = authUseCase.register(usuario);
        return ResponseEntity.ok(savedUsuario);
    }
}
