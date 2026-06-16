package com.policlinico.smartsalud.application.ports.input.catalogo;

import com.policlinico.smartsalud.shared.dto.response.catalogo.EspecialidadResponse;
import com.policlinico.smartsalud.shared.dto.response.catalogo.MedicoListResponse;
import com.policlinico.smartsalud.shared.dto.response.catalogo.SedeResponse;
import java.util.List;

public interface CatalogoInputPort {
    List<EspecialidadResponse> listarEspecialidades();
    List<SedeResponse> listarSedes();
    List<MedicoListResponse> listarMedicos(Long especialidadId);
}
