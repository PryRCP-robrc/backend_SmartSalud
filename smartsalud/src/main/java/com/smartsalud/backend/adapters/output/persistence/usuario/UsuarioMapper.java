package com.smartsalud.backend.adapters.output.persistence.usuario;

import com.smartsalud.backend.domain.model.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "rol.nombre", target = "rol")
    Usuario toDomain(UsuarioEntity entity);

    @Mapping(source = "rol", target = "rol.nombre")
    UsuarioEntity toEntity(Usuario domain);
}
