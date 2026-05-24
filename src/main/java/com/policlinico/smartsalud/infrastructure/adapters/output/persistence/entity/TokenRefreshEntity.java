// infrastructure/adapters/output/persistence/entity/TokenRefreshEntity.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_refresh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 20)
    private String entidad;
    
    @Column(name = "entidad_id", nullable = false)
    private Long entidadId;
    
    @Column(unique = true, nullable = false, columnDefinition = "TEXT")
    private String token;
    
    @Column(name = "expira_en", nullable = false)
    private LocalDateTime expiraEn;
    
    private Boolean revocado;
    
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;
}