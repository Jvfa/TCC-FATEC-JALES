package br.com.sif.sif.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import br.com.sif.sif.dto.ProcessoRequestDTO;
import br.com.sif.sif.entity.Processo;
import br.com.sif.sif.service.ProcessoService;

@RestController
@RequestMapping("/api/processos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProcessoController {
    private final ProcessoService processoService;

    public ProcessoController(ProcessoService processoService) {
        this.processoService = processoService;
    }

    // POST /api/processos
    @PostMapping
    public ResponseEntity<Processo> criarNovoProcesso(@RequestBody ProcessoRequestDTO request) {
        Processo novoProcesso = processoService.criarProcesso(
            request.pacienteId(),
            request.processo(),
            request.itens()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
    }
    
    // GET /api/processos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Processo> buscarProcessoPorId(@PathVariable Long id) {
        Processo processo = processoService.buscarPorId(id);
        return ResponseEntity.ok(processo);
    }

    // GET /api/processos/paciente/{pacienteId}
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Processo>> buscarProcessosDoPaciente(@PathVariable Long pacienteId) {
        List<Processo> processos = processoService.buscarProcessosPorPaciente(pacienteId);
        return ResponseEntity.ok(processos);
    }

}
