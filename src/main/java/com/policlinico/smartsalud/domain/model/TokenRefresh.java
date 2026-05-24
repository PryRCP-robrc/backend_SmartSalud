// domain/model/TokenRefresh.java
package com.policlinico.smartsalud.domain.model;

import com.policlinico.smartsalud.domain.model.enums.EstadoToken;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TokenRefresh {
    private Long id;
    private String entidad;
    private Long entidadId;
    private String token;
    private LocalDateTime expiraEn;
    private EstadoToken estado;
    private LocalDateTime creadoEn;
    
    public boolean isExpirado() {
        return LocalDateTime.now().isAfter(expiraEn);
    }
    
    public boolean isActivo() {
        return estado == EstadoToken.ACTIVO && !isExpirado();
    }
}