// application/service/auth/MedicoAuthService.java
package com.policlinico.smartsalud.application.service.auth;

import com.policlinico.smartsalud.application.ports.input.auth.MedicoAuthInputPort;
import com.policlinico.smartsalud.domain.model.Rol;
import com.policlinico.smartsalud.domain.model.TokenRefresh;
import com.policlinico.smartsalud.domain.model.UsuarioPrincipal;
import com.policlinico.smartsalud.domain.model.enums.NombreRol;
import com.policlinico.smartsalud.domain.model.medico.Medico;
import com.policlinico.smartsalud.domain.ports.output.MedicoRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.UsuarioRolRepositoryPort;
import com.policlinico.smartsalud.infrastructure.config.security.JwtTokenProvider;
import com.policlinico.smartsalud.shared.dto.request.auth.LoginRequest;
import com.policlinico.smartsalud.shared.dto.response.auth.MedicoAuthResponse;
import com.policlinico.smartsalud.shared.dto.response.medico.MedicoResponse;
import com.policlinico.smartsalud.shared.exception.BadCredentialsException;
import com.policlinico.smartsalud.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoAuthService implements MedicoAuthInputPort {

    private static final String ENTIDAD = "MEDICO";

    private final MedicoRepositoryPort medicoRepository;
    private final UsuarioRolRepositoryPort usuarioRolRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MedicoAuthResponse login(LoginRequest request) {
        log.info("Intento de login médico con email: {}", request.getEmail());

        Medico medico = medicoRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email o contraseña incorrectos"));

        if (medico.getPasswordHash() == null || medico.getPasswordHash().isBlank()) {
            log.error("Médico {} no tiene password_hash configurado en BD", request.getEmail());
            throw new BadCredentialsException("La cuenta del médico no tiene credenciales configuradas. Contacte al administrador.");
        }

        if (!passwordEncoder.matches(request.getPassword(), medico.getPasswordHash())) {
            log.warn("Contraseña incorrecta para médico: {}", request.getEmail());
            throw new BadCredentialsException("Email o contraseña incorrectos");
        }

        if (medico.getActivo() != null && !medico.getActivo()) {
            log.warn("Médico inactivo: {}", request.getEmail());
            throw new BadCredentialsException("Médico inactivo. Contacte al administrador.");
        }

        List<Rol> roles = usuarioRolRepository.findRolesByEntidad(ENTIDAD, medico.getId());
        if (roles == null || roles.isEmpty()) {
            // Garantizamos al menos el rol MEDICO para emitir el JWT
            roles = List.of(Rol.builder().nombre(NombreRol.MEDICO.name()).build());
        }

        UsuarioPrincipal usuarioConRoles = UsuarioPrincipal.builder()
                .id(medico.getId())
                .dni(medico.getDni())
                .nombres(medico.getNombres())
                .apellidos(medico.getApellidos())
                .email(medico.getEmail())
                .telefono(medico.getTelefono())
                .password(medico.getPasswordHash())
                .activo(true)
                .roles(roles)
                .build();

        String accessToken = tokenProvider.generateAccessToken(usuarioConRoles);
        TokenRefresh refreshToken = refreshTokenService.createRefreshToken(medico.getId(), ENTIDAD);

        log.info("Login médico exitoso para email: {}", request.getEmail());

        return MedicoAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tipo("Bearer")
                .expiresIn(tokenProvider.getAccessTokenExpirationMs())
                .medicoId(medico.getId())
                .nombres(medico.getNombres())
                .apellidos(medico.getApellidos())
                .email(medico.getEmail())
                .cmp(medico.getCmp())
                .especialidad(medico.getEspecialidadNombre())
                .rol(NombreRol.MEDICO.name())
                .build();
    }

    @Override
    public MedicoResponse me(String email) {
        log.info("GET /api/auth/medico/me — email: {}", email);
        Medico m = medicoRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con email: " + email));
        return MedicoResponse.builder()
                .id(m.getId())
                .nombres(m.getNombres())
                .apellidos(m.getApellidos())
                .cmp(m.getCmp())
                .especialidad(m.getEspecialidadNombre())
                .email(m.getEmail())
                .telefono(m.getTelefono())
                .fotoUrl(m.getFotoUrl())
                .aniosExperiencia(m.getAniosExperiencia())
                .descripcionProfesional(m.getDescripcionProfesional())
                .build();
    }
}
