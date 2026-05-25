package com.policlinico.smartsalud.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
    @NotBlank String dni,
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @Email String email,
    String telefono,
    String password,
    @NotBlank String rolNombre // ADMIN o RECEPCION
) {}
