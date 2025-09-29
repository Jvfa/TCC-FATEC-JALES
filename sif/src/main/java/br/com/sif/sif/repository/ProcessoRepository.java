package br.com.sif.sif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sif.sif.entity.Processo;
import br.com.sif.sif.entity.enums.StatusProcesso;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long>{
        /**
     * Busca todos os processos associados a um ID de paciente.
     * @param pacienteId O ID do paciente.
     * @return Uma lista de seus Processos.
     */
    List<Processo> findByPaciente_Id(Long pacienteId);

    /**
     * Busca todos os processos que correspondem a um status específico.
     * @param status O status do processo (ex: EM_ABERTO).
     * @return Uma lista de Processos com o status informado.
     */
    List<Processo> findByStatus(StatusProcesso status);
}
