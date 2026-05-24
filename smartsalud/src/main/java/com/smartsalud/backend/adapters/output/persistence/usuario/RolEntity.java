package com.smartsalud.backend.adapters.output.persistence.usuario;

import com.smartsalud.backend.domain.model.usuario.Rol;
import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Rol nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rol getNombre() {
        return nombre;
    }

    public void setNombre(Rol nombre) {
        this.nombre = nombre;
    }
}
