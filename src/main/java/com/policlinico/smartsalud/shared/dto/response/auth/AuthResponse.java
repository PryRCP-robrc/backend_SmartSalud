// shared/dto/response/auth/AuthResponse.java
package com.policlinico.smartsalud.shared.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tipo;
    private Long expiresIn;
}