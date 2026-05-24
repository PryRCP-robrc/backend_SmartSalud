package com.smartsalud.backend.application.service.auth;

import com.smartsalud.backend.domain.model.usuario.Usuario;
import com.smartsalud.backend.domain.port.in.auth.AuthUseCase;
import com.smartsalud.backend.domain.port.out.usuario.UsuarioRepositoryPort;
import com.smartsalud.backend.shared.config.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String login(String email, String password) {
        Usuario usuario = usuarioRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        return jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());
    }

    @Override
    public Usuario register(Usuario usuario) {
        if (usuarioRepositoryPort.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepositoryPort.save(usuario);
    }
}
