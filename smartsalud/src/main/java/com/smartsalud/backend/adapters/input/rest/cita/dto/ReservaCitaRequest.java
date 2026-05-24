package com.smartsalud.backend.adapters.input.rest.cita.dto;

import java.time.LocalDateTime;

public class ReservaCitaRequest {
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime fechaHora;
    private String observaciones;

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
