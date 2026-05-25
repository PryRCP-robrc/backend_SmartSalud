// infrastructure/adapters/output/persistence/adapter/CitaRepositoryAdapter.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.adapter;

import com.policlinico.smartsalud.domain.model.cita.Cita;
import com.policlinico.smartsalud.domain.ports.output.CitaRepositoryPort;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper.CitaMapper;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaCitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CitaRepositoryAdapter implements CitaRepositoryPort {

    private final JpaCitaRepository jpaRepository;
    private final CitaMapper mapper;

    @Override
    public List<Cita> findByMedicoId(Long medicoId) {
        return jpaRepository.findByMedicoIdOrderByFechaDescHoraAsc(medicoId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cita> findByMedicoIdAndFecha(Long medicoId, LocalDate fecha) {
        return jpaRepository.findByMedicoIdAndFecha(medicoId, fecha).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cita> findByMedicoIdAndFechaBetween(Long medicoId, LocalDate desde, LocalDate hasta) {
        return jpaRepository.findByMedicoIdAndFechaBetween(medicoId, desde, hasta).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findDistinctPacienteIdsByMedicoId(Long medicoId) {
        return jpaRepository.findDistinctPacienteIdsByMedicoId(medicoId);
    }
}
