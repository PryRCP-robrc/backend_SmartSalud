// application/ports/input/auth/AuthInputPort.java
package com.policlinico.smartsalud.application.ports.input.auth;

import com.policlinico.smartsalud.shared.dto.request.auth.*;
import com.policlinico.smartsalud.shared.dto.response.auth.AuthResponse;
import com.policlinico.smartsalud.shared.dto.response.auth.MeResponse;

public interface AuthInputPort {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
    void logout(RefreshTokenRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    MeResponse getCurrentUser(String email);
    void changePassword(ChangePasswordRequest request, String email);
}