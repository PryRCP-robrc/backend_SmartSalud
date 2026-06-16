package com.policlinico.smartsalud.infrastructure.adapters.input.rest.pago;

import com.policlinico.smartsalud.application.service.pago.PagoService;
import com.policlinico.smartsalud.shared.dto.request.pago.CrearPagoRequest;
import com.policlinico.smartsalud.shared.dto.response.pago.PagoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pago")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponse> procesar(@Valid @RequestBody CrearPagoRequest request) {
        return ResponseEntity.ok(pagoService.procesarPago(request));
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<PagoResponse> obtenerPorCita(@PathVariable Long citaId) {
        return ResponseEntity.ok(pagoService.obtenerPorCita(citaId));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<PagoResponse>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(pagoService.listarPorPaciente(pacienteId));
    }
}
