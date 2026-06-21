package com.policlinico.smartsalud.application.service.catalogo;

import com.policlinico.smartsalud.application.ports.input.catalogo.CatalogoInputPort;
import com.policlinico.smartsalud.domain.ports.output.EspecialidadRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.MedicoRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.SedeRepositoryPort;
import com.policlinico.smartsalud.shared.dto.response.catalogo.EspecialidadResponse;
import com.policlinico.smartsalud.shared.dto.response.catalogo.MedicoListResponse;
import com.policlinico.smartsalud.shared.dto.response.catalogo.SedeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogoService implements CatalogoInputPort {

    private final EspecialidadRepositoryPort especialidadRepo;
    private final SedeRepositoryPort sedeRepo;
    private final MedicoRepositoryPort medicoRepo;

    @Override
    public List<EspecialidadResponse> listarEspecialidades() {
        return especialidadRepo.findAllActivas().stream()
                .map(e -> EspecialidadResponse.builder()
                        .id(e.getId())
                        .nombre(e.getNombre())
                        .descripcion(e.getDescripcion())
                        .iconoUrl(e.getIconoUrl())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<SedeResponse> listarSedes() {
        return sedeRepo.findAllActivas().stream()
                .map(s -> SedeResponse.builder()
                        .id(s.getId())
                        .nombre(s.getNombre())
                        .direccion(s.getDireccion())
                        .distrito(s.getDistrito())
                        .ciudad(s.getCiudad())
                        .telefono(s.getTelefono())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicoListResponse> listarMedicos(Long especialidadId) {
        return medicoRepo.findAllActivos(especialidadId).stream()
                .map(m -> MedicoListResponse.builder()
                        .id(m.getId())
                        .nombres(m.getNombres())
                        .apellidos(m.getApellidos())
                        .cmp(m.getCmp())
                        .especialidadId(m.getEspecialidadId())
                        .especialidadNombre(m.getEspecialidadNombre())
                        .email(m.getEmail())
                        .telefono(m.getTelefono())
                        .fotoUrl(m.getFotoUrl())
                        .aniosExperiencia(m.getAniosExperiencia())
                        .descripcionProfesional(m.getDescripcionProfesional())
                        .tarifaConsulta(m.getTarifaConsulta())
                        .build())
                .collect(Collectors.toList());
    }
}
