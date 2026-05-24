// shared/dto/response/auth/MeResponse.java
package com.policlinico.smartsalud.shared.dto.response.auth;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MeResponse {
    private Long id;
    private String email;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private List<String> roles;
}