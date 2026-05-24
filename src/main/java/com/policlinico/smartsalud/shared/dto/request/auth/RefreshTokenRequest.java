// shared/dto/request/auth/RefreshTokenRequest.java
package com.policlinico.smartsalud.shared.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token es obligatorio")
    private String refreshToken;
}