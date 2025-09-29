package br.com.sif.sif.controller;


import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.sif.sif.dto.PacienteUpdateDTO;
import br.com.sif.sif.entity.Paciente;
import br.com.sif.sif.service.PacienteService;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "http://localhost:4200")
public class PacienteController {
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // POST /api/pacientes
    @PostMapping
    public ResponseEntity<Paciente> criarPreCadastro(@RequestBody Paciente paciente) {
        Paciente novoPaciente = pacienteService.criarPreCadastro(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
    }

    // GET /api/pacientes
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes(@RequestParam Map<String, String> filtros) {
        List<Paciente> pacientes = pacienteService.buscarComFiltros(filtros);
        return ResponseEntity.ok(pacientes);
    }

    // GET /api/pacientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(paciente);
    }

    // PUT /api/pacientes/{id}/aprovar
    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Paciente> aprovarCadastro(@PathVariable Long id) {
        Paciente pacienteAprovado = pacienteService.aprovarCadastro(id);
        return ResponseEntity.ok(pacienteAprovado);
    }

    @PutMapping("/{id}")
public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @RequestBody PacienteUpdateDTO dto) {
    Paciente pacienteAtualizado = pacienteService.atualizar(id, dto);
    return ResponseEntity.ok(pacienteAtualizado);
}

    @GetMapping("/pendentes")
    public ResponseEntity<List<Paciente>> getCadastrosPendentes() {
        List<Paciente> pacientesPendentes = pacienteService.listarCadastrosPendentes();
        return ResponseEntity.ok(pacientesPendentes);
    }

}
