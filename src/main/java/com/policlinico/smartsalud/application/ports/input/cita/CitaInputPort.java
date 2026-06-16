package com.policlinico.smartsalud.application.ports.input.cita;

import com.policlinico.smartsalud.shared.dto.request.cita.CancelarCitaRequest;
import com.policlinico.smartsalud.shared.dto.request.cita.CrearCitaRequest;
import com.policlinico.smartsalud.shared.dto.response.cita.CitaResponse;
import com.policlinico.smartsalud.shared.dto.response.horario.SlotDisponibleResponse;

import java.time.LocalDate;
import java.util.List;

public interface CitaInputPort {
    CitaResponse crear(CrearCitaRequest request);
    CitaResponse obtenerPorId(Long id);
    List<CitaResponse> listarPorPaciente(Long pacienteId);
    CitaResponse cancelar(Long id, CancelarCitaRequest request);
    List<SlotDisponibleResponse> slotsDisponibles(Long medicoId, LocalDate fecha);
}
