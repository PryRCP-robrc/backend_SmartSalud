package com.policlinico.smartsalud.infrastructure.adapters.input.rest.catalogo;

import com.policlinico.smartsalud.application.ports.input.catalogo.CatalogoInputPort;
import com.policlinico.smartsalud.shared.dto.response.catalogo.EspecialidadResponse;
import com.policlinico.smartsalud.shared.dto.response.catalogo.MedicoListResponse;
import com.policlinico.smartsalud.shared.dto.response.catalogo.SedeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoInputPort catalogoService;

    @GetMapping("/especialidad")
    public ResponseEntity<List<EspecialidadResponse>> listarEspecialidades() {
        return ResponseEntity.ok(catalogoService.listarEspecialidades());
    }

    @GetMapping("/sede")
    public ResponseEntity<List<SedeResponse>> listarSedes() {
        return ResponseEntity.ok(catalogoService.listarSedes());
    }

    @GetMapping("/medico")
    public ResponseEntity<List<MedicoListResponse>> listarMedicos(
            @RequestParam(required = false) Long especialidadId) {
        return ResponseEntity.ok(catalogoService.listarMedicos(especialidadId));
    }
}
