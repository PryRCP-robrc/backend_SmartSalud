package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_clinico")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialClinicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @Column(name = "cita_id")
    private Long citaId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "motivo_consulta")
    private String motivoConsulta;

    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    @Column(name = "proxima_cita")
    private LocalDate proximaCita;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;
}
