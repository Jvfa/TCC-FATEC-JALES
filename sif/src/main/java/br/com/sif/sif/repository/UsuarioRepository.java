package br.com.sif.sif.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sif.sif.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    /**
     * Busca um usuário pelo seu login para o processo de autenticação.
     * @param login O login do usuário.
     * @return Um Optional contendo o Usuário, se encontrado.
     */
    Optional<Usuario> findByLogin(String login);
}
