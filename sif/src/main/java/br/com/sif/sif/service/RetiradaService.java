package br.com.sif.sif.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sif.sif.entity.Processo;
import br.com.sif.sif.entity.Retirada;
import br.com.sif.sif.entity.Usuario;
import br.com.sif.sif.entity.enums.StatusProcesso;
import br.com.sif.sif.repository.ProcessoRepository;
import br.com.sif.sif.repository.RetiradaRepository;
import br.com.sif.sif.repository.UsuarioRepository;
import br.com.sif.sif.service.exception.ResourceNotFoundException;

@Service
public class RetiradaService {

    private final RetiradaRepository retiradaRepository;
    private final ProcessoRepository processoRepository;
    private final UsuarioRepository usuarioRepository;

    public RetiradaService(RetiradaRepository retiradaRepository, ProcessoRepository processoRepository, UsuarioRepository usuarioRepository) {
        this.retiradaRepository = retiradaRepository;
        this.processoRepository = processoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * O @Transactional garante que todas as operações com o banco de dados
     * dentro deste método sejam executadas em uma única transação.
     * Se qualquer erro ocorrer, todas as operações são desfeitas (rollback),
     * garantindo a consistência dos dados.
     */
    @Transactional
    public Retirada registrarRetirada(Long processoId, Long atendenteId, String nomeRetirou, Integer quantidade) {
        // 1. Busca as entidades necessárias
        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new ResourceNotFoundException("Processo não encontrado com o ID: " + processoId));

        Usuario atendente = usuarioRepository.findById(atendenteId)
                .orElseThrow(() -> new ResourceNotFoundException("Atendente não encontrado com o ID: " + atendenteId));

        // 2. Aplica as regras de negócio
        if (processo.getStatus() != StatusProcesso.EM_ABERTO) {
            throw new IllegalStateException("Não é possível registrar retirada para um processo que não está em aberto.");
        }
        
        // 3. Cria e preenche a nova entidade de Retirada
        Retirada novaRetirada = new Retirada();
        novaRetirada.setProcesso(processo);
        novaRetirada.setAtendente(atendente);
        novaRetirada.setDataRetirada(LocalDate.now());
        novaRetirada.setNomeRetirou(nomeRetirou);
        novaRetirada.setQuantidadeDispensada(quantidade);

        // 4. Salva a nova retirada no banco de dados
        return retiradaRepository.save(novaRetirada);
    }
}
