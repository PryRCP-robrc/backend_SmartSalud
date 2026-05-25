// infrastructure/adapters/input/rest/medico/MedicoController.java
package com.policlinico.smartsalud.infrastructure.adapters.input.rest.medico;

import com.policlinico.smartsalud.application.ports.input.medico.MedicoInputPort;
import com.policlinico.smartsalud.shared.dto.response.medico.CitaMedicoResponse;
import com.policlinico.smartsalud.shared.dto.response.medico.MedicoResponse;
import com.policlinico.smartsalud.shared.dto.response.medico.PacienteMedicoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/medico")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoInputPort medicoService;

    // Perfil del médico autenticado
    @GetMapping("/me")
    @PreAuthorize("hasRole('MEDICO')")
    public ResponseEntity<MedicoResponse> getMiPerfil(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicoService.getMedicoByEmail(userDetails.getUsername()));
    }

    // Perfil de un médico por ID (accesible por MEDICO, RECEPCIONISTA y ADMIN)
    @GetMapping("/{medicoId}")
    @PreAuthorize("hasAnyRole('MEDICO','RECEPCIONISTA','ADMIN','SUPER_ADMIN')")
    public ResponseEntity<MedicoResponse> getMedicoById(@PathVariable Long medicoId) {
        return ResponseEntity.ok(medicoService.getMedicoById(medicoId));
    }

    // Todas las citas del médico (historial completo)
    @GetMapping("/{medicoId}/citas")
    @PreAuthorize("hasAnyRole('MEDICO','RECEPCIONISTA','ADMIN','SUPER_ADMIN')")
    public ResponseEntity<List<CitaMedicoResponse>> getCitasByMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(medicoService.getCitasByMedico(medicoId));
    }

    // Agenda del día (o de una fecha específica)
    @GetMapping("/{medicoId}/agenda")
    @PreAuthorize("hasAnyRole('MEDICO','RECEPCIONISTA','ADMIN','SUPER_ADMIN')")
    public ResponseEntity<List<CitaMedicoResponse>> getAgenda(
            @PathVariable Long medicoId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        if (desde != null && hasta != null) {
            return ResponseEntity.ok(medicoService.getAgendaByRango(medicoId, desde, hasta));
        }

        LocalDate fechaConsulta = (fecha != null) ? fecha : LocalDate.now();
        return ResponseEntity.ok(medicoService.getAgendaByFecha(medicoId, fechaConsulta));
    }

    // Lista de pacientes atendidos por el médico
    @GetMapping("/{medicoId}/pacientes")
    @PreAuthorize("hasAnyRole('MEDICO','RECEPCIONISTA','ADMIN','SUPER_ADMIN')")
    public ResponseEntity<List<PacienteMedicoResponse>> getPacientesByMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(medicoService.getPacientesByMedico(medicoId));
    }
}
