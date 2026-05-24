// shared/dto/request/auth/ChangePasswordRequest.java
package com.policlinico.smartsalud.shared.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Contraseña actual es obligatoria")
    private String currentPassword;

    @NotBlank(message = "Nueva contraseña es obligatoria")
    @Size(min = 6, max = 20, message = "Nueva contraseña debe tener entre 6 y 20 caracteres")
    private String newPassword;
}