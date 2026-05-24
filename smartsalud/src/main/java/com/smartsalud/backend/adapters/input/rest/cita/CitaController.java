package com.smartsalud.backend.adapters.input.rest.cita;

import com.smartsalud.backend.adapters.input.rest.cita.dto.ReservaCitaRequest;
import com.smartsalud.backend.domain.model.cita.Cita;
import com.smartsalud.backend.domain.port.in.cita.ReservaCitaUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final ReservaCitaUseCase reservaCitaUseCase;

    public CitaController(ReservaCitaUseCase reservaCitaUseCase) {
        this.reservaCitaUseCase = reservaCitaUseCase;
    }

    @PostMapping("/reservar")
    public ResponseEntity<Cita> reservar(@RequestBody ReservaCitaRequest request) {
        Cita cita = new Cita(
                null,
                request.getPacienteId(),
                request.getMedicoId(),
                request.getFechaHora(),
                null,
                request.getObservaciones()
        );
        return ResponseEntity.ok(reservaCitaUseCase.reservarCita(cita));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaCitaUseCase.cancelarCita(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Cita>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(reservaCitaUseCase.obtenerCitasPorPaciente(pacienteId));
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Cita>> obtenerPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(reservaCitaUseCase.obtenerCitasPorMedico(medicoId));
    }
}
