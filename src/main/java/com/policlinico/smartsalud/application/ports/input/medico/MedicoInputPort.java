// application/ports/input/medico/MedicoInputPort.java
package com.policlinico.smartsalud.application.ports.input.medico;

import com.policlinico.smartsalud.shared.dto.response.medico.CitaMedicoResponse;
import com.policlinico.smartsalud.shared.dto.response.medico.MedicoResponse;
import com.policlinico.smartsalud.shared.dto.response.medico.PacienteMedicoResponse;
import java.time.LocalDate;
import java.util.List;

public interface MedicoInputPort {
    MedicoResponse getMedicoById(Long medicoId);
    MedicoResponse getMedicoByEmail(String email);
    List<CitaMedicoResponse> getCitasByMedico(Long medicoId);
    List<CitaMedicoResponse> getAgendaByFecha(Long medicoId, LocalDate fecha);
    List<CitaMedicoResponse> getAgendaByRango(Long medicoId, LocalDate desde, LocalDate hasta);
    List<PacienteMedicoResponse> getPacientesByMedico(Long medicoId);
}
