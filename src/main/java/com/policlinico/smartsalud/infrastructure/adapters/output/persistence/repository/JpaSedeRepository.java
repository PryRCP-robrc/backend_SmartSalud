package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.SedeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaSedeRepository extends JpaRepository<SedeEntity, Long> {
    List<SedeEntity> findByActivaTrueOrderByNombreAsc();
}
