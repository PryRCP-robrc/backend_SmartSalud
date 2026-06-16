package com.policlinico.smartsalud.shared.dto.request.pago;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CrearPagoRequest {
    @NotNull
    private Long citaId;

    @NotNull
    @Positive
    private BigDecimal monto;

    private String moneda = "PEN";

    @NotNull
    private String metodo; // EFECTIVO | TARJETA_CREDITO | TARJETA_DEBITO | YAPE | PLIN | TRANSFERENCIA | SEGURO_MEDICO

    private String referenciaExterna;
}
