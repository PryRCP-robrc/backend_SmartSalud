// application/ports/input/auth/MedicoAuthInputPort.java
package com.policlinico.smartsalud.application.ports.input.auth;

import com.policlinico.smartsalud.shared.dto.request.auth.LoginRequest;
import com.policlinico.smartsalud.shared.dto.response.auth.MedicoAuthResponse;
import com.policlinico.smartsalud.shared.dto.response.medico.MedicoResponse;

public interface MedicoAuthInputPort {
    MedicoAuthResponse login(LoginRequest request);
    MedicoResponse me(String email);
}
