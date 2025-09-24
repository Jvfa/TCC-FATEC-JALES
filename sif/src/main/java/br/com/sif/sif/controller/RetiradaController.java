package br.com.sif.sif.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sif.sif.dto.RetiradaRequestDTO;
import br.com.sif.sif.entity.Retirada;
import br.com.sif.sif.service.RetiradaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/retiradas")
@CrossOrigin(origins = "http://localhost:4200")
public class RetiradaController {
    private final RetiradaService retiradaService;

    public RetiradaController(RetiradaService retiradaService) {
        this.retiradaService = retiradaService;
    }

    // POST /api/retiradas
    @PostMapping
    public ResponseEntity<Retirada> registrarNovaRetirada(@RequestBody RetiradaRequestDTO request) {
        Retirada novaRetirada = retiradaService.registrarRetirada(
            request.processoId(),
            request.atendenteId(),
            request.nomeRetirou(),
            request.quantidade()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRetirada);
    }
}
