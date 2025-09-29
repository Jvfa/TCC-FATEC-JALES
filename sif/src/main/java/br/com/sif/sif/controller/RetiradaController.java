package br.com.sif.sif.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // IMPORTANTE
import org.springframework.security.core.context.SecurityContextHolder; // IMPORTANTE
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sif.sif.dto.RetiradaRequestDTO;
import br.com.sif.sif.entity.Retirada;
import br.com.sif.sif.entity.Usuario;
import br.com.sif.sif.service.RetiradaService;

@RestController
@RequestMapping("/api/retiradas")
public class RetiradaController {

    private final RetiradaService retiradaService;

    public RetiradaController(RetiradaService retiradaService) {
        this.retiradaService = retiradaService;
    }

    @PostMapping
    public ResponseEntity<Retirada> registrarNovaRetirada(@RequestBody RetiradaRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario atendente = (Usuario) authentication.getPrincipal();

        Retirada novaRetirada = retiradaService.registrarRetirada(
            request.processoId(),
            atendente, // Passamos o usuário que pegamos manualmente
            request.nomeRetirou(),
            request.quantidade()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRetirada);
    }
}
