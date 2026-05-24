// application/service/auth/AuthService.java
package com.policlinico.smartsalud.application.service.auth;

import com.policlinico.smartsalud.application.ports.input.auth.AuthInputPort;
import com.policlinico.smartsalud.domain.model.Rol;
import com.policlinico.smartsalud.domain.model.TokenRefresh;
import com.policlinico.smartsalud.domain.model.UsuarioPrincipal;
import com.policlinico.smartsalud.domain.model.enums.NombreRol;
import com.policlinico.smartsalud.domain.ports.output.PacienteRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.RolRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.UsuarioRolRepositoryPort;
import com.policlinico.smartsalud.infrastructure.config.security.JwtTokenProvider;
import com.policlinico.smartsalud.shared.dto.request.auth.*;
import com.policlinico.smartsalud.shared.dto.response.auth.AuthResponse;
import com.policlinico.smartsalud.shared.dto.response.auth.MeResponse;
import com.policlinico.smartsalud.shared.exception.BadCredentialsException;
import com.policlinico.smartsalud.shared.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthInputPort {
    
    private final PacienteRepositoryPort pacienteRepository;
    private final RolRepositoryPort rolRepository;
    private final UsuarioRolRepositoryPort usuarioRolRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Intento de login con email: {}", request.getEmail());
        
        UsuarioPrincipal usuario = pacienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email o contraseña incorrectos"));
        
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            log.warn("Contraseña incorrecta para email: {}", request.getEmail());
            throw new BadCredentialsException("Email o contraseña incorrectos");
        }
        
        if (!usuario.isEnabled()) {
            log.warn("Usuario inactivo: {}", request.getEmail());
            throw new BadCredentialsException("Usuario inactivo. Contacte al administrador.");
        }
        
        // Obtener roles actualizados del usuario
        List<Rol> roles = usuarioRolRepository.findRolesByEntidad("PACIENTE", usuario.getId());
        log.info("Roles encontrados para usuario {}: {}", usuario.getEmail(), 
                 roles.stream().map(Rol::getNombre).collect(Collectors.joining(",")));
        
        // Construir usuario completo con roles
        UsuarioPrincipal usuarioConRoles = UsuarioPrincipal.builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .password(usuario.getPassword())
                .activo(usuario.isEnabled())
                .roles(roles)
                .build();
        
        String accessToken = tokenProvider.generateAccessToken(usuarioConRoles);
        TokenRefresh refreshToken = refreshTokenService.createRefreshToken(usuario.getId(), "PACIENTE");
        
        log.info("Login exitoso para email: {}", request.getEmail());
        
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tipo("Bearer")
                .expiresIn(tokenProvider.getAccessTokenExpirationMs())
                .build();
    }
    
    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registro de nuevo usuario con email: {}", request.getEmail());
        
        // Validar existencia
        if (pacienteRepository.existsByEmail(request.getEmail())) {
            log.warn("Email ya registrado: {}", request.getEmail());
            throw new DuplicateResourceException("El email ya está registrado");
        }
        
        if (pacienteRepository.existsByDni(request.getDni())) {
            log.warn("DNI ya registrado: {}", request.getDni());
            throw new DuplicateResourceException("El DNI ya está registrado");
        }
        
        // Crear usuario (sin roles aún)
        UsuarioPrincipal nuevoUsuario = UsuarioPrincipal.builder()
                .dni(request.getDni())
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .password(passwordEncoder.encode(request.getPassword()))
                .activo(true)
                .roles(new ArrayList<>())  // Inicializar lista vacía
                .build();
        
        UsuarioPrincipal savedUser = pacienteRepository.save(nuevoUsuario);
        log.info("Usuario creado con ID: {}", savedUser.getId());
        
        // Asignar rol PACIENTE
        Rol pacienteRol = rolRepository.findByNombre(NombreRol.PACIENTE)
                .orElseThrow(() -> {
                    log.error("Rol PACIENTE no encontrado en la base de datos");
                    return new RuntimeException("Rol PACIENTE no encontrado");
                });
        
        usuarioRolRepository.assignRolToUsuario("PACIENTE", savedUser.getId(), pacienteRol.getId());
        log.info("Rol {} asignado al usuario ID: {}", pacienteRol.getNombre(), savedUser.getId());
        
        // Obtener los roles del usuario recién creado
        List<Rol> roles = usuarioRolRepository.findRolesByEntidad("PACIENTE", savedUser.getId());
        log.info("Roles obtenidos para el nuevo usuario: {}", 
                 roles.stream().map(Rol::getNombre).collect(Collectors.joining(",")));
        
        // Construir usuario completo con roles para el token
        UsuarioPrincipal usuarioConRoles = UsuarioPrincipal.builder()
                .id(savedUser.getId())
                .dni(savedUser.getDni())
                .nombres(savedUser.getNombres())
                .apellidos(savedUser.getApellidos())
                .email(savedUser.getEmail())
                .telefono(savedUser.getTelefono())
                .password(savedUser.getPassword())
                .activo(savedUser.isEnabled())
                .roles(roles)
                .build();
        
        // Generar tokens con el usuario que TIENE roles
        String accessToken = tokenProvider.generateAccessToken(usuarioConRoles);
        TokenRefresh refreshToken = refreshTokenService.createRefreshToken(usuarioConRoles.getId(), "PACIENTE");
        
        log.info("Registro exitoso para email: {}", request.getEmail());
        
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tipo("Bearer")
                .expiresIn(tokenProvider.getAccessTokenExpirationMs())
                .build();
    }
    
    @Override
    public void logout(RefreshTokenRequest request) {
        log.info("Solicitud de logout");
        TokenRefresh refreshToken = refreshTokenService.findByToken(request.getRefreshToken());
        refreshTokenService.deleteByUserId(refreshToken.getEntidadId(), refreshToken.getEntidad());
        log.info("Logout exitoso para usuario ID: {}", refreshToken.getEntidadId());
    }
    
    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        log.info("Solicitud de refresh token");
        
        TokenRefresh refreshToken = refreshTokenService.findByToken(request.getRefreshToken());
        refreshTokenService.verifyExpiration(refreshToken);
        
        UsuarioPrincipal usuario = pacienteRepository.findById(refreshToken.getEntidadId())
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con ID: {}", refreshToken.getEntidadId());
                    return new RuntimeException("Usuario no encontrado");
                });
        
        // Obtener roles actualizados del usuario
        List<Rol> roles = usuarioRolRepository.findRolesByEntidad("PACIENTE", usuario.getId());
        
        // Construir usuario completo con roles
        UsuarioPrincipal usuarioConRoles = UsuarioPrincipal.builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .password(usuario.getPassword())
                .activo(usuario.isEnabled())
                .roles(roles)
                .build();
        
        String newAccessToken = tokenProvider.generateAccessToken(usuarioConRoles);
        
        log.info("Refresh token exitoso para usuario ID: {}", usuario.getId());
        
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .tipo("Bearer")
                .expiresIn(tokenProvider.getAccessTokenExpirationMs())
                .build();
    }
    
    @Override
    public MeResponse getCurrentUser(String email) {
        log.info("Obteniendo información del usuario: {}", email);
        
        UsuarioPrincipal usuario = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con email: {}", email);
                    return new RuntimeException("Usuario no encontrado");
                });
        
        List<Rol> roles = usuarioRolRepository.findRolesByEntidad("PACIENTE", usuario.getId());
        
        log.info("Usuario encontrado: {} con roles: {}", email, 
                 roles.stream().map(Rol::getNombre).collect(Collectors.joining(",")));
        
        return MeResponse.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .dni(usuario.getDni())
                .telefono(usuario.getTelefono())
                .roles(roles.stream().map(Rol::getNombre).collect(Collectors.toList()))
                .build();
    }
    
    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request, String email) {
        log.info("Cambio de contraseña para usuario: {}", email);
        
        UsuarioPrincipal usuario = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con email: {}", email);
                    return new RuntimeException("Usuario no encontrado");
                });
        
        if (!passwordEncoder.matches(request.getCurrentPassword(), usuario.getPassword())) {
            log.warn("Contraseña actual incorrecta para usuario: {}", email);
            throw new BadCredentialsException("Contraseña actual incorrecta");
        }
        
        UsuarioPrincipal updatedUser = UsuarioPrincipal.builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .password(passwordEncoder.encode(request.getNewPassword()))
                .activo(usuario.isEnabled())
                .roles(new ArrayList<>()) // Temporal, se actualizará después
                .build();
        
        pacienteRepository.save(updatedUser);
        
        // Revocar todos los refresh tokens por seguridad
        refreshTokenService.deleteByUserId(usuario.getId(), "PACIENTE");
        
        log.info("Contraseña cambiada exitosamente para usuario: {}", email);
    }
}