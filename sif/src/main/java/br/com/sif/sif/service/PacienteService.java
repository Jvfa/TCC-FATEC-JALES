package br.com.sif.sif.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.sif.sif.dto.PacienteCreateDTO;
import br.com.sif.sif.dto.PacienteUpdateDTO;
import br.com.sif.sif.entity.Paciente;
import br.com.sif.sif.entity.enums.StatusCadastro;
import br.com.sif.sif.repository.PacienteRepository;
import br.com.sif.sif.repository.PacienteSpecification;
import br.com.sif.sif.service.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    // Injeção de dependência via construtor
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    // 2. Altere a assinatura do método para receber o DTO
    public Paciente criarPreCadastro(PacienteCreateDTO dto) {

        // (Opcional, mas recomendado) Manter a regra de negócio para não duplicar CPF
        if (pacienteRepository.findByCpf(dto.cpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um paciente cadastrado com este CPF.");
        }

        // 3. Crie uma nova instância da ENTIDADE Paciente
        Paciente novoPaciente = new Paciente();

        // 4. Copie os dados do DTO (que veio da requisição) para a Entidade
        novoPaciente.setNome(dto.nome());
        novoPaciente.setNomeMae(dto.nomeMae());
        novoPaciente.setDataNascimento(dto.dataNascimento());
        novoPaciente.setCpf(dto.cpf());
        novoPaciente.setRg(dto.rg());
        novoPaciente.setCns(dto.cns());
        novoPaciente.setCep(dto.cep());
        novoPaciente.setCidade(dto.cidade());
        novoPaciente.setBairro(dto.bairro());
        novoPaciente.setRua(dto.rua());
        novoPaciente.setNumero(dto.numero());
        novoPaciente.setComplemento(dto.complemento());
        novoPaciente.setTelefone(dto.telefone());
        novoPaciente.setPeso(dto.peso());
        novoPaciente.setAltura(dto.altura());
        novoPaciente.setCor(dto.cor());

        // 5. Defina os campos que são controlados pelo backend e não pelo usuário
        novoPaciente.setStatusCadastro(StatusCadastro.PRE_CADASTRO);

        // 6. Salve a ENTIDADE (e não o DTO) no banco de dados
        return pacienteRepository.save(novoPaciente);
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
    public Paciente atualizar(Long id, PacienteUpdateDTO dto) {
        Paciente pacienteExistente = buscarPorId(id);

        // Copia os dados do DTO para a entidade gerenciada pelo JPA
        pacienteExistente.setNome(dto.nome());
        pacienteExistente.setNomeMae(dto.nomeMae());
        pacienteExistente.setDataNascimento(dto.dataNascimento());
        pacienteExistente.setRg(dto.rg());
        pacienteExistente.setCns(dto.cns());
        pacienteExistente.setCep(dto.cep());
        pacienteExistente.setCidade(dto.cidade());
        pacienteExistente.setBairro(dto.bairro());
        pacienteExistente.setRua(dto.rua());
        pacienteExistente.setNumero(dto.numero());
        pacienteExistente.setComplemento(dto.complemento());
        pacienteExistente.setTelefone(dto.telefone());
        pacienteExistente.setPeso(dto.peso());
        pacienteExistente.setAltura(dto.altura());
        pacienteExistente.setCor(dto.cor());

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

    // ... outros métodos ...
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorDataNascimento(LocalDate data) {
        return pacienteRepository.findByDataNascimento(data);
    }

    @Transactional(readOnly = true)
    public List<Paciente> buscarPorNomeDaMae(String nomeMae) {
        return pacienteRepository.findByNomeMaeContainingIgnoreCase(nomeMae);
    }

    @Transactional(readOnly = true)
    public List<Paciente> buscarComFiltros(Map<String, String> filtros) {
        Specification<Paciente> spec = PacienteSpecification.comFiltros(filtros);
        return pacienteRepository.findAll(spec);
    }

}
