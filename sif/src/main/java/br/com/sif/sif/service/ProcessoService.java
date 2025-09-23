package br.com.sif.sif.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sif.sif.dto.ItemProcessoDTO;
import br.com.sif.sif.entity.ItemProcesso;
import br.com.sif.sif.entity.Medicamento;
import br.com.sif.sif.entity.Paciente;
import br.com.sif.sif.entity.Processo;
import br.com.sif.sif.entity.enums.StatusCadastro;
import br.com.sif.sif.entity.enums.StatusProcesso;
import br.com.sif.sif.repository.MedicamentoRepository;
import br.com.sif.sif.repository.PacienteRepository;
import br.com.sif.sif.repository.ProcessoRepository;
import br.com.sif.sif.service.exception.ResourceNotFoundException;

@Service
public class ProcessoService {
    
    private final ProcessoRepository processoRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicamentoRepository medicamentoRepository;

    public ProcessoService(ProcessoRepository processoRepository, PacienteRepository pacienteRepository,
            MedicamentoRepository medicamentoRepository) {
        this.processoRepository = processoRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    /**
     * Cria um novo processo para um paciente, incluindo os medicamentos associados.
     * A anotação @Transactional garante que todas as operações (buscar paciente,
     * salvar processo,
     * salvar itens) sejam concluídas com sucesso. Se qualquer uma falhar, tudo é
     * desfeito (rollback).
     */
    @Transactional
    public Processo criarProcesso(Long pacienteId, Processo novoProcesso, List<ItemProcessoDTO> itensDto) {
        // 1. Buscar o paciente e validar seu status
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com o ID: " + pacienteId));

        if (paciente.getStatusCadastro() != StatusCadastro.ATIVO) {
            throw new IllegalStateException("Só é possível abrir processos para pacientes com cadastro ATIVO.");
        }

        // 2. Associar o processo ao paciente e definir o status inicial
        novoProcesso.setPaciente(paciente);
        novoProcesso.setStatus(StatusProcesso.EM_ABERTO);

        // 3. Processar e associar os medicamentos ao processo
        Set<ItemProcesso> itens = new HashSet<>();
        for (ItemProcessoDTO itemDto : itensDto) {
            Medicamento medicamento = medicamentoRepository.findById(itemDto.medicamentoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Medicamento não encontrado com o ID: " + itemDto.medicamentoId()));

            ItemProcesso item = new ItemProcesso();
            item.setProcesso(novoProcesso); // Associa o item ao processo que está sendo criado
            item.setMedicamento(medicamento);
            item.setQuantidade(itemDto.quantidade());
            itens.add(item);
        }
        novoProcesso.setItensMedicamentos(itens);

        // 4. Salvar o processo. Graças ao CascadeType.ALL na entidade Processo,
        // os ItensProcesso também serão salvos automaticamente.
        return processoRepository.save(novoProcesso);
    }

    @Transactional(readOnly = true)
    public Processo buscarPorId(Long id) {
        return processoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Processo não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Processo> buscarProcessosPorPaciente(Long pacienteId) {
        return processoRepository.findByPacienteId(pacienteId);
    }

    // Você pode adicionar outros métodos como fecharProcesso, cancelarProcesso,
    // etc.
}
