// shared/dto/request/auth/RegisterRequest.java
package com.policlinico.smartsalud.shared.dto.request.auth;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequest {
    @NotBlank(message = "DNI es obligatorio")
    @Size(min = 8, max = 15, message = "DNI debe tener entre 8 y 15 caracteres")
    private String dni;

    @NotBlank(message = "Nombres son obligatorios")
    @Size(min = 2, max = 100, message = "Nombres debe tener entre 2 y 100 caracteres")
    private String nombres;

    @NotBlank(message = "Apellidos son obligatorios")
    @Size(min = 2, max = 100, message = "Apellidos debe tener entre 2 y 100 caracteres")
    private String apellidos;

    @NotNull(message = "Fecha de nacimiento es obligatoria")
    @Past(message = "Fecha de nacimiento debe ser pasada")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "Sexo es obligatorio")
    @Pattern(regexp = "^[MF]$", message = "Sexo debe ser M o F")
    private String sexo;

    @NotBlank(message = "Email es obligatorio")
    @Email(message = "Email debe tener formato válido")
    private String email;

    @NotBlank(message = "Teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{9}$", message = "Teléfono debe tener 9 dígitos")
    private String telefono;

    @NotBlank(message = "Contraseña es obligatoria")
    @Size(min = 6, max = 20, message = "Contraseña debe tener entre 6 y 20 caracteres")
    private String password;
}