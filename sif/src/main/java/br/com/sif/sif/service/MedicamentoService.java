package br.com.sif.sif.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sif.sif.entity.Medicamento;
import br.com.sif.sif.repository.MedicamentoRepository;
import br.com.sif.sif.service.exception.ResourceNotFoundException;

@Service
public class MedicamentoService {
     private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @Transactional
    public Medicamento cadastrarMedicamento(Medicamento medicamento) {
        // Regra de negócio opcional: verificar se já não existe um medicamento com mesmo nome e dosagem
        // (Isso dependeria de uma query customizada no repository)
        return medicamentoRepository.save(medicamento);
    }

    @Transactional(readOnly = true)
    public Medicamento buscarPorId(Long id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Medicamento> listarTodos() {
        return medicamentoRepository.findAll();
    }

    @Transactional
    public Medicamento atualizarMedicamento(Long id, Medicamento medicamentoAtualizado) {
        Medicamento medicamentoExistente = buscarPorId(id);
        
        medicamentoExistente.setNome(medicamentoAtualizado.getNome());
        medicamentoExistente.setDosagem(medicamentoAtualizado.getDosagem());
        medicamentoExistente.setFormaFarmaceutica(medicamentoAtualizado.getFormaFarmaceutica());
        
        return medicamentoRepository.save(medicamentoExistente);
    }

    @Transactional
    public void deletarMedicamento(Long id) {
        if (!medicamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medicamento não encontrado com o ID: " + id);
        }
        
        // Regra de negócio importante: Verificar se o medicamento não está em uso em algum processo.
        // Se estiver, a exclusão deve ser proibida para manter a integridade do histórico.
        // (Isso exigiria uma consulta no ItemProcessoRepository)
        // Ex: if (itemProcessoRepository.existsByMedicamentoId(id)) {
        //         throw new IllegalStateException("Não é possível excluir um medicamento que está em uso.");
        //     }
        
        medicamentoRepository.deleteById(id);
    }
}
