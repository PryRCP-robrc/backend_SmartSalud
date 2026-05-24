package com.smartsalud.backend.domain.port.in.auth;

import com.smartsalud.backend.domain.model.usuario.Usuario;

public interface AuthUseCase {
    String login(String email, String password);
    Usuario register(Usuario usuario);
}
