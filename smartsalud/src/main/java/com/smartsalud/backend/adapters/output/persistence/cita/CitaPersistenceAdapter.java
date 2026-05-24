package com.smartsalud.backend.adapters.output.persistence.cita;

import com.smartsalud.backend.domain.model.cita.Cita;
import com.smartsalud.backend.domain.port.out.cita.CitaRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CitaPersistenceAdapter implements CitaRepositoryPort {

    private final CitaJpaRepository citaJpaRepository;
    private final CitaMapper citaMapper;

    public CitaPersistenceAdapter(CitaJpaRepository citaJpaRepository, CitaMapper citaMapper) {
        this.citaJpaRepository = citaJpaRepository;
        this.citaMapper = citaMapper;
    }

    @Override
    public Cita save(Cita cita) {
        CitaEntity entity = citaMapper.toEntity(cita);
        CitaEntity savedEntity = citaJpaRepository.save(entity);
        return citaMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Cita> findById(Long id) {
        return citaJpaRepository.findById(id)
                .map(citaMapper::toDomain);
    }

    @Override
    public List<Cita> findByPacienteId(Long pacienteId) {
        return citaMapper.toDomainList(citaJpaRepository.findByPacienteId(pacienteId));
    }

    @Override
    public List<Cita> findByMedicoId(Long medicoId) {
        return citaMapper.toDomainList(citaJpaRepository.findByMedicoId(medicoId));
    }

    @Override
    public boolean existsByMedicoIdAndFechaHoraBetween(Long medicoId, LocalDateTime start, LocalDateTime end) {
        return citaJpaRepository.existsByMedicoIdAndFechaHoraBetween(medicoId, start, end);
    }
}
