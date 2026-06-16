package com.policlinico.smartsalud.infrastructure.adapters.input.rest.cita;

import com.policlinico.smartsalud.application.ports.input.cita.CitaInputPort;
import com.policlinico.smartsalud.shared.dto.request.cita.CancelarCitaRequest;
import com.policlinico.smartsalud.shared.dto.request.cita.CrearCitaRequest;
import com.policlinico.smartsalud.shared.dto.response.cita.CitaResponse;
import com.policlinico.smartsalud.shared.dto.response.horario.SlotDisponibleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cita")
@RequiredArgsConstructor
public class CitaController {

    private final CitaInputPort citaService;

    @PostMapping
    public ResponseEntity<CitaResponse> crear(@Valid @RequestBody CrearCitaRequest request) {
        return ResponseEntity.ok(citaService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<CitaResponse> cancelar(@PathVariable Long id, @Valid @RequestBody CancelarCitaRequest request) {
        return ResponseEntity.ok(citaService.cancelar(id, request));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaResponse>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(citaService.listarPorPaciente(pacienteId));
    }
}
