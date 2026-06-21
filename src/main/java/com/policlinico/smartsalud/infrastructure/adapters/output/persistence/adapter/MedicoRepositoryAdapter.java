// infrastructure/adapters/output/persistence/adapter/MedicoRepositoryAdapter.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.adapter;

import com.policlinico.smartsalud.domain.model.medico.Medico;
import com.policlinico.smartsalud.domain.ports.output.MedicoRepositoryPort;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper.MedicoMapper;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaMedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MedicoRepositoryAdapter implements MedicoRepositoryPort {

    private final JpaMedicoRepository jpaRepository;
    private final MedicoMapper mapper;

    @Override
    public Optional<Medico> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Medico> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public List<Medico> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public List<Medico> findAllActivos(Long especialidadId) {
        var entities = (especialidadId != null)
                ? jpaRepository.findAllActivosPorEspecialidad(especialidadId)
                : jpaRepository.findAllActivos();

        // tarifa por medico (medico_id -> monto) para exponer el precio real de la consulta
        Map<Long, BigDecimal> tarifas = jpaRepository.findTarifasPorMedico().stream()
                .collect(Collectors.toMap(
                        r -> ((Number) r[0]).longValue(),
                        r -> (BigDecimal) r[1],
                        (a, b) -> a));

        return entities.stream()
                .map(mapper::toDomain)
                .peek(m -> m.setTarifaConsulta(tarifas.get(m.getId())))
                .collect(Collectors.toList());
    }
}
