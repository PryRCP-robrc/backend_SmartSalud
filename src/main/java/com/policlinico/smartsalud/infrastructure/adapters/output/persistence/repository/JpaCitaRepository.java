package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface JpaCitaRepository extends JpaRepository<CitaEntity, Long> {

    // Médico
    @Query("SELECT c FROM CitaEntity c JOIN FETCH c.paciente JOIN FETCH c.medico m JOIN FETCH m.especialidad WHERE c.medico.id = :medicoId ORDER BY c.fecha DESC, c.hora ASC")
    List<CitaEntity> findByMedicoIdOrderByFechaDescHoraAsc(@Param("medicoId") Long medicoId);

    @Query("SELECT c FROM CitaEntity c JOIN FETCH c.paciente JOIN FETCH c.medico m JOIN FETCH m.especialidad WHERE c.medico.id = :medicoId AND c.fecha = :fecha ORDER BY c.hora ASC")
    List<CitaEntity> findByMedicoIdAndFecha(@Param("medicoId") Long medicoId, @Param("fecha") LocalDate fecha);

    @Query("SELECT c FROM CitaEntity c JOIN FETCH c.paciente JOIN FETCH c.medico m JOIN FETCH m.especialidad WHERE c.medico.id = :medicoId AND c.fecha BETWEEN :desde AND :hasta ORDER BY c.fecha ASC, c.hora ASC")
    List<CitaEntity> findByMedicoIdAndFechaBetween(@Param("medicoId") Long medicoId, @Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    @Query("SELECT DISTINCT c.paciente.id FROM CitaEntity c WHERE c.medico.id = :medicoId AND c.estado NOT IN ('CANCELADO', 'NO_ASISTIO')")
    List<Long> findDistinctPacienteIdsByMedicoId(@Param("medicoId") Long medicoId);

    // Paciente
    @Query("SELECT c FROM CitaEntity c JOIN FETCH c.paciente JOIN FETCH c.medico m JOIN FETCH m.especialidad WHERE c.paciente.id = :pacienteId ORDER BY c.fecha DESC, c.hora ASC")
    List<CitaEntity> findByPacienteIdOrderByFechaDesc(@Param("pacienteId") Long pacienteId);

    // Disponibilidad
    boolean existsByMedicoIdAndFechaAndHora(Long medicoId, LocalDate fecha, LocalTime hora);

    @Query("SELECT c.hora FROM CitaEntity c WHERE c.medico.id = :medicoId AND c.fecha = :fecha AND c.estado NOT IN ('CANCELADO','NO_ASISTIO')")
    List<LocalTime> findOcupadasByMedicoAndFecha(@Param("medicoId") Long medicoId, @Param("fecha") LocalDate fecha);
}
