package com.policlinico.smartsalud.infrastructure.adapters.input.rest.horario;

import com.policlinico.smartsalud.application.ports.input.cita.CitaInputPort;
import com.policlinico.smartsalud.shared.dto.response.horario.SlotDisponibleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/horario")
@RequiredArgsConstructor
public class HorarioController {

    private final CitaInputPort citaService;

    @GetMapping("/disponibles")
    public ResponseEntity<List<SlotDisponibleResponse>> slotsDisponibles(
            @RequestParam Long medicoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(citaService.slotsDisponibles(medicoId, fecha));
    }
}
