package com.policlinico.smartsalud.infrastructure.adapters.input.rest.historial;

import com.policlinico.smartsalud.application.service.historial.HistorialClinicoService;
import com.policlinico.smartsalud.shared.dto.request.historial.CrearHistorialRequest;
import com.policlinico.smartsalud.shared.dto.response.historial.HistorialClinicoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
public class HistorialClinicoController {

    private final HistorialClinicoService service;

    @PostMapping
    public ResponseEntity<HistorialClinicoResponse> crear(@Valid @RequestBody CrearHistorialRequest request) {
        return ResponseEntity.ok(service.crear(request));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistorialClinicoResponse>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.listarPorPaciente(pacienteId));
    }
}
