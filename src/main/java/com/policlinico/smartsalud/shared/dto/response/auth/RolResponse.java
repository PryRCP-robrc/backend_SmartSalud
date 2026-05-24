// shared/dto/response/auth/RolResponse.java
package com.policlinico.smartsalud.shared.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RolResponse {
    private Long id;
    private String nombre;
    private String descripcion;
}