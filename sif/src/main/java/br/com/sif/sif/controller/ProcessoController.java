package br.com.sif.sif.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        // 3. Crie a entidade Processo a partir do DTO
        Processo novoProcesso = new Processo();
        novoProcesso.setMesInicioValidade(request.processo().mesInicioValidade());
        novoProcesso.setMesFimValidade(request.processo().mesFimValidade());
        novoProcesso.setCid(request.processo().cid());
        novoProcesso.setObservacoes(request.processo().observacoes());
        novoProcesso.setDataAbertura(request.processo().dataAbertura());

        // 4. Chame o serviço com os dados organizados
        Processo processoSalvo = processoService.criarProcesso(
                request.pacienteId(),
                novoProcesso,
                request.itens() // A lista de itens já está no formato correto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(processoSalvo);
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

    @PutMapping("/{id}/vencer") // Endpoint: PUT /api/processos/{id}/vencer
    public ResponseEntity<Processo> marcarComoVencido(@PathVariable Long id) {
        Processo processoAtualizado = processoService.marcarComoVencido(id);
        return ResponseEntity.ok(processoAtualizado);
    }

}
