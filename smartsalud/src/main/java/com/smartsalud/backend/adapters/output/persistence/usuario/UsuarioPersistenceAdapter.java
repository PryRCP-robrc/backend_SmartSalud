package com.smartsalud.backend.adapters.output.persistence.usuario;

import com.smartsalud.backend.domain.model.usuario.Usuario;
import com.smartsalud.backend.domain.port.out.usuario.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final RolJpaRepository rolJpaRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioPersistenceAdapter(UsuarioJpaRepository usuarioJpaRepository, 
                                     RolJpaRepository rolJpaRepository,
                                     UsuarioMapper usuarioMapper) {
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.rolJpaRepository = rolJpaRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioJpaRepository.findByEmail(email)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = usuarioMapper.toEntity(usuario);
        
        // Ensure Rol is fetched and set properly before saving
        if (usuario.getRol() != null) {
            RolEntity rolEntity = rolJpaRepository.findByNombre(usuario.getRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + usuario.getRol()));
            entity.setRol(rolEntity);
        }

        UsuarioEntity savedEntity = usuarioJpaRepository.save(entity);
        return usuarioMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioJpaRepository.existsByEmail(email);
    }
}
