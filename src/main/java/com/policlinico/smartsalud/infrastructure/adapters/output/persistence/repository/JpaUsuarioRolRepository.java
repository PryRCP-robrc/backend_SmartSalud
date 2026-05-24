// infrastructure/adapters/output/persistence/repository/JpaUsuarioRolRepository.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.UsuarioRolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JpaUsuarioRolRepository extends JpaRepository<UsuarioRolEntity, Long> {
    List<UsuarioRolEntity> findByEntidadAndEntidadId(String entidad, Long entidadId);
    
    @Modifying
    @Query("DELETE FROM UsuarioRolEntity ur WHERE ur.entidad = :entidad AND ur.entidadId = :entidadId")
    void deleteByEntidadAndEntidadId(@Param("entidad") String entidad, @Param("entidadId") Long entidadId);
}