package com.policlinico.smartsalud.shared.dto.response.pago;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PagoResponse {
    private Long id;
    private Long citaId;
    private BigDecimal monto;
    private String moneda;
    private String metodo;
    private String estado;
    private String referenciaExterna;
    private LocalDateTime fecha;
    private LocalDateTime fechaConfirmacion;
    private Integer intentos;
}
