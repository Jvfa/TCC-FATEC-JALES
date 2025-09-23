package br.com.sif.sif.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sif.sif.entity.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{
    /**
     * Busca um paciente pelo seu CPF. Retorna um Optional, pois o paciente pode não existir.
     * O Spring Data JPA cria a query automaticamente a partir do nome do método.
     * @param cpf O CPF a ser buscado.
     * @return Um Optional contendo o Paciente, se encontrado.
     */
    Optional<Paciente> findByCpf(String cpf);

    /**
     * Busca pacientes cujo nome contém o texto fornecido, ignorando maiúsculas/minúsculas.
     * Útil para a funcionalidade de busca na interface.
     * @param nome O texto a ser buscado no nome dos pacientes.
     * @return Uma lista de Pacientes que correspondem ao critério.
     */
    List<Paciente> findByNomeContainingIgnoreCase(String nome);
}
