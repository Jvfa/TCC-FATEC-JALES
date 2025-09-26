package br.com.sif.sif.controller;

import br.com.sif.sif.entity.Medicamento;
import br.com.sif.sif.service.MedicamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
// Não precisamos do @CrossOrigin aqui, pois já temos uma configuração global no SecurityConfig
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    // Endpoint para listar todos os medicamentos (GET /api/medicamentos)
    @GetMapping
    public ResponseEntity<List<Medicamento>> listarTodos() {
        List<Medicamento> medicamentos = medicamentoService.listarTodos();
        return ResponseEntity.ok(medicamentos);
    }

    // Endpoint para buscar um medicamento por ID (GET /api/medicamentos/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscarPorId(@PathVariable Long id) {
        Medicamento medicamento = medicamentoService.buscarPorId(id);
        return ResponseEntity.ok(medicamento);
    }

    // Endpoint para cadastrar um novo medicamento (POST /api/medicamentos)
    @PostMapping
    public ResponseEntity<Medicamento> cadastrar(@RequestBody Medicamento medicamento) {
        Medicamento novoMedicamento = medicamentoService.cadastrarMedicamento(medicamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedicamento);
    }

    // Endpoint para atualizar um medicamento existente (PUT /api/medicamentos/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> atualizar(@PathVariable Long id, @RequestBody Medicamento medicamento) {
        Medicamento medicamentoAtualizado = medicamentoService.atualizarMedicamento(id, medicamento);
        return ResponseEntity.ok(medicamentoAtualizado);
    }

    // Endpoint para deletar um medicamento (DELETE /api/medicamentos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicamentoService.deletarMedicamento(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content, que é o padrão para delete
    }
}