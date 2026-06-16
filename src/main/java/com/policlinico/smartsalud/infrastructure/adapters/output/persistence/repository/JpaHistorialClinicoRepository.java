package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.HistorialClinicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaHistorialClinicoRepository extends JpaRepository<HistorialClinicoEntity, Long> {
    List<HistorialClinicoEntity> findByPacienteIdOrderByFechaDesc(Long pacienteId);
    List<HistorialClinicoEntity> findByMedicoIdOrderByFechaDesc(Long medicoId);
}
