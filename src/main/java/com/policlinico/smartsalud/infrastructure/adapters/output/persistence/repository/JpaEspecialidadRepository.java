package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.EspecialidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaEspecialidadRepository extends JpaRepository<EspecialidadEntity, Long> {
    List<EspecialidadEntity> findByActivaTrueOrderByNombreAsc();
}
