// infrastructure/adapters/output/persistence/repository/JpaTokenRefreshRepository.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.TokenRefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface JpaTokenRefreshRepository extends JpaRepository<TokenRefreshEntity, Long> {
    Optional<TokenRefreshEntity> findByToken(String token);
    
    @Modifying
    @Query("DELETE FROM TokenRefreshEntity t WHERE t.entidad = :entidad AND t.entidadId = :entidadId")
    void deleteByEntidadAndEntidadId(String entidad, Long entidadId);
    
    @Modifying
    @Query("UPDATE TokenRefreshEntity t SET t.revocado = true WHERE t.entidad = :entidad AND t.entidadId = :entidadId")
    void revokeAllByEntidadAndEntidadId(String entidad, Long entidadId);
}