package com.smartsalud.backend.domain.port.out.usuario;

import com.smartsalud.backend.domain.model.usuario.Usuario;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Optional<Usuario> findByEmail(String email);
    Usuario save(Usuario usuario);
    boolean existsByEmail(String email);
}
