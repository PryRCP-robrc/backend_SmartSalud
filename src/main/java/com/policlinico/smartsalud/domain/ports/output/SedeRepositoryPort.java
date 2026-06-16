package com.policlinico.smartsalud.domain.ports.output;

import com.policlinico.smartsalud.domain.model.catalogo.Sede;
import java.util.List;
import java.util.Optional;

public interface SedeRepositoryPort {
    List<Sede> findAllActivas();
    Optional<Sede> findById(Long id);
}
