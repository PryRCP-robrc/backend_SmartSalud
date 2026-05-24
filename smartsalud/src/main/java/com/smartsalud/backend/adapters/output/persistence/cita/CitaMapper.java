package com.smartsalud.backend.adapters.output.persistence.cita;

import com.smartsalud.backend.domain.model.cita.Cita;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CitaMapper {
    Cita toDomain(CitaEntity entity);
    CitaEntity toEntity(Cita domain);
    List<Cita> toDomainList(List<CitaEntity> entities);
}
