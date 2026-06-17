package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.dto.SedeDTO;
import com.policlinico.smartsalud.application.service.SedeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sedes")
@RequiredArgsConstructor
public class SedeController {

    private final SedeService service;

    @GetMapping
    public ResponseEntity<List<SedeDTO>> getSedes() {
        List<SedeDTO> list = service.getSedesActivas().stream()
            .map(s -> new SedeDTO(s.getId(), s.getNombre(), s.getDireccion(), s.getDistrito(), s.getCiudad()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @org.springframework.web.bind.annotation.PostMapping
    public ResponseEntity<SedeDTO> createSede(@jakarta.validation.Valid @org.springframework.web.bind.annotation.RequestBody com.policlinico.smartsalud.application.dto.SedeRequest request) {
        return ResponseEntity.ok(service.createSede(request));
    }

    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    public ResponseEntity<SedeDTO> updateSede(@org.springframework.web.bind.annotation.PathVariable Integer id, @jakarta.validation.Valid @org.springframework.web.bind.annotation.RequestBody com.policlinico.smartsalud.application.dto.SedeRequest request) {
        return ResponseEntity.ok(service.updateSede(id, request));
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSede(@org.springframework.web.bind.annotation.PathVariable Integer id) {
        service.deleteSede(id);
        return ResponseEntity.noContent().build();
    }
}
