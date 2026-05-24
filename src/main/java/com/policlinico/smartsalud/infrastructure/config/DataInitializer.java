// infrastructure/config/DataInitializer.java
package com.policlinico.smartsalud.infrastructure.config;

import com.policlinico.smartsalud.domain.model.Rol;
import com.policlinico.smartsalud.domain.model.enums.NombreRol;
import com.policlinico.smartsalud.domain.ports.output.RolRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final RolRepositoryPort rolRepository;
    
    @Override
    @Transactional
    public void run(String... args) {
        // Inicializar roles si no existen
        for (NombreRol nombreRol : NombreRol.values()) {
            if (rolRepository.findByNombre(nombreRol).isEmpty()) {
                Rol rol = Rol.builder()
                        .nombre(nombreRol.name())
                        .descripcion("Rol: " + nombreRol.name())
                        .build();
                rolRepository.save(rol);
                log.info("Rol creado: {}", nombreRol);
            }
        }
        
        log.info("DataInitializer: Roles verificados correctamente");
    }
}