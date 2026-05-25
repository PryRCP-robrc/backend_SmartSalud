// infrastructure/adapters/output/persistence/entity/CitaEntity.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "cita")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteEntity paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private MedicoEntity medico;

    @Column(name = "sede_id", nullable = false)
    private Long sedeId;

    @Column(name = "sala_id")
    private Long salaId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(name = "duracion_min", nullable = false)
    private Integer duracionMin;

    @Column(name = "tipo_consulta", nullable = false, length = 50)
    private String tipoConsulta;

    @Column(nullable = false, length = 20)
    private String modalidad;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(name = "motivo_consulta")
    private String motivoConsulta;

    @Column(name = "notas_admin")
    private String notasAdmin;

    @Column(name = "codigo_reserva", nullable = false, length = 20)
    private String codigoReserva;

    @Column(nullable = false, length = 20)
    private String origen;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_cancelacion")
    private LocalDateTime fechaCancelacion;

    @Column(name = "motivo_cancelacion", length = 255)
    private String motivoCancelacion;

    @Column(name = "cancelado_por", length = 50)
    private String canceladoPor;
}
