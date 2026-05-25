// shared/dto/response/auth/MedicoAuthResponse.java
package com.policlinico.smartsalud.shared.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicoAuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tipo;
    private Long expiresIn;

    // Datos básicos del médico para hidratar el frontend sin segunda llamada
    private Long medicoId;
    private String nombres;
    private String apellidos;
    private String email;
    private String cmp;
    private String especialidad;
    private String rol; // siempre "MEDICO"
}
