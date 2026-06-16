package com.policlinico.smartsalud.application.service.pago;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.PagoEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaPagoRepository;
import com.policlinico.smartsalud.shared.dto.request.pago.CrearPagoRequest;
import com.policlinico.smartsalud.shared.dto.response.pago.PagoResponse;
import com.policlinico.smartsalud.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagoService {

    private final JpaPagoRepository pagoRepo;

    @Transactional
    public PagoResponse procesarPago(CrearPagoRequest request) {
        log.info("Procesando pago cita={} metodo={}", request.getCitaId(), request.getMetodo());

        // Simulación: tarjeta y billeteras electrónicas se aprueban inmediatamente,
        // transferencia bancaria queda PENDIENTE hasta verificación manual.
        boolean confirmadoInmediato = !request.getMetodo().equals("TRANSFERENCIA")
                && !request.getMetodo().equals("EFECTIVO")
                && !request.getMetodo().equals("SEGURO_MEDICO");

        PagoEntity pago = PagoEntity.builder()
                .citaId(request.getCitaId())
                .monto(request.getMonto())
                .moneda(request.getMoneda() != null ? request.getMoneda() : "PEN")
                .metodo(request.getMetodo())
                .estado(confirmadoInmediato ? "COMPLETADO" : "PENDIENTE")
                .referenciaExterna(request.getReferenciaExterna() != null
                        ? request.getReferenciaExterna()
                        : "REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .fecha(LocalDateTime.now())
                .fechaConfirmacion(confirmadoInmediato ? LocalDateTime.now() : null)
                .intentos(1)
                .build();

        PagoEntity saved = pagoRepo.save(pago);
        return toResponse(saved);
    }

    public PagoResponse obtenerPorCita(Long citaId) {
        return pagoRepo.findByCitaId(citaId)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado para cita: " + citaId));
    }

    public List<PagoResponse> listarPorPaciente(Long pacienteId) {
        return pagoRepo.findAllByPacienteId(pacienteId).stream().map(this::toResponse).toList();
    }

    private PagoResponse toResponse(PagoEntity e) {
        return PagoResponse.builder()
                .id(e.getId())
                .citaId(e.getCitaId())
                .monto(e.getMonto())
                .moneda(e.getMoneda())
                .metodo(e.getMetodo())
                .estado(e.getEstado())
                .referenciaExterna(e.getReferenciaExterna())
                .fecha(e.getFecha())
                .fechaConfirmacion(e.getFechaConfirmacion())
                .intentos(e.getIntentos())
                .build();
    }
}
