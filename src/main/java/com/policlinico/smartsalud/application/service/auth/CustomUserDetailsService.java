// application/service/auth/CustomUserDetailsService.java
package com.policlinico.smartsalud.application.service.auth;

import com.policlinico.smartsalud.domain.model.Rol;
import com.policlinico.smartsalud.domain.model.UsuarioPrincipal;
import com.policlinico.smartsalud.domain.model.enums.NombreRol;
import com.policlinico.smartsalud.domain.model.medico.Medico;
import com.policlinico.smartsalud.domain.ports.output.MedicoRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.PacienteRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.UsuarioRolRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Polimórfico: intenta cargar el usuario primero como Paciente; si no existe,
 * lo busca como Médico. Esto permite que el filtro JWT autentique a usuarios
 * de cualquier tipo a partir del email del token.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PacienteRepositoryPort pacienteRepository;
    private final MedicoRepositoryPort medicoRepository;
    private final UsuarioRolRepositoryPort usuarioRolRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1) Intento paciente
        var pacienteOpt = pacienteRepository.findByEmail(email);
        if (pacienteOpt.isPresent()) {
            UsuarioPrincipal up = pacienteOpt.get();
            List<Rol> roles = usuarioRolRepository.findRolesByEntidad("PACIENTE", up.getId());
            up.setRoles(roles);
            return up;
        }

        // 2) Fallback médico
        var medicoOpt = medicoRepository.findByEmail(email);
        if (medicoOpt.isPresent()) {
            Medico m = medicoOpt.get();
            List<Rol> roles = usuarioRolRepository.findRolesByEntidad("MEDICO", m.getId());
            // Si no se le ha asignado el rol explícitamente, garantizamos al menos MEDICO
            if (roles == null || roles.isEmpty()) {
                roles = List.of(Rol.builder().nombre(NombreRol.MEDICO.name()).build());
            }
            return UsuarioPrincipal.builder()
                    .id(m.getId())
                    .dni(m.getDni())
                    .nombres(m.getNombres())
                    .apellidos(m.getApellidos())
                    .email(m.getEmail())
                    .telefono(m.getTelefono())
                    .password(m.getPasswordHash())
                    .activo(m.getActivo() != null ? m.getActivo() : true)
                    .roles(roles)
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
    }
}
