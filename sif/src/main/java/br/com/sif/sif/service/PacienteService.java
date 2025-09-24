package br.com.sif.sif.service;

import org.springframework.stereotype.Service;

import br.com.sif.sif.entity.Paciente;
import br.com.sif.sif.entity.enums.StatusCadastro;
import br.com.sif.sif.repository.PacienteRepository;
import br.com.sif.sif.service.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    // Injeção de dependência via construtor
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public Paciente criarPreCadastro(Paciente paciente) {
        // Regra de negócio: Verificar se o CPF já existe
        Optional<Paciente> pacienteExistente = pacienteRepository.findByCpf(paciente.getCpf());
        if (pacienteExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe um paciente cadastrado com este CPF.");
        }

        // Regra de negócio: Definir o status inicial como PRE_CADASTRO
        paciente.setStatusCadastro(StatusCadastro.PRE_CADASTRO);
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public Paciente aprovarCadastro(Long id) {
        // Busca o paciente ou lança uma exceção se não encontrar
        Paciente paciente = buscarPorId(id);

        // Regra de negócio: Só aprova se estiver em PRE_CADASTRO
        if (paciente.getStatusCadastro() != StatusCadastro.PRE_CADASTRO) {
            throw new IllegalStateException("Este cadastro não está pendente de aprovação.");
        }

        paciente.setStatusCadastro(StatusCadastro.ATIVO);
        return pacienteRepository.save(paciente);
    }

    @Transactional(readOnly = true) // readOnly = true otimiza a transação para operações de leitura
    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf);
    }

    @Transactional
    public Paciente atualizar(Long id, Paciente pacienteAtualizado) {
        // Primeiro, busca o paciente existente no banco. O método buscarPorId já lança
        // uma exceção se não encontrar, o que é perfeito para nós.
        Paciente pacienteExistente = buscarPorId(id);

        // Copia as propriedades do objeto recebido para o objeto que já existe no
        // banco.
        // Não atualizamos o ID, CPF ou o status por este método.
        pacienteExistente.setNome(pacienteAtualizado.getNome());
        pacienteExistente.setNomeMae(pacienteAtualizado.getNomeMae());
        pacienteExistente.setDataNascimento(pacienteAtualizado.getDataNascimento());
        pacienteExistente.setRg(pacienteAtualizado.getRg());
        pacienteExistente.setCns(pacienteAtualizado.getCns());
        pacienteExistente.setEndereco(pacienteAtualizado.getEndereco());
        pacienteExistente.setTelefone(pacienteAtualizado.getTelefone());
        pacienteExistente.setPeso(pacienteAtualizado.getPeso());
        pacienteExistente.setAltura(pacienteAtualizado.getAltura());
        pacienteExistente.setCor(pacienteAtualizado.getCor());

        // Salva o objeto atualizado. O JPA entende que é uma atualização por causa do
        // ID.
        return pacienteRepository.save(pacienteExistente);
    }

    @Transactional(readOnly = true)
    public List<Paciente> listarCadastrosPendentes() {
        return pacienteRepository.findByStatusCadastro(StatusCadastro.PRE_CADASTRO);
    }

    @Transactional(readOnly = true)
    public List<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeContainingIgnoreCase(nome);
    }
}
