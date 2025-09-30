package br.com.sif.sif.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.sif.sif.entity.Usuario;
import br.com.sif.sif.repository.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService  {
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Este método é chamado pelo Spring Security quando um usuário tenta fazer login.
     * Ele busca o usuário no banco de dados pelo login.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("--- DENTRO DO AuthorizationService ---");
        System.out.println("Buscando usuário com login: '" + username + "'");

        // Buscamos o usuário e guardamos em uma variável para inspecionar
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(username);

        if (usuarioOptional.isPresent()) {
            System.out.println("SUCESSO: Usuário '" + username + "' foi encontrado no banco de dados!");
            return usuarioOptional.get();
        } else {
            // Se a busca não retornar nada, este erro será impresso
            System.err.println("FALHA: Usuário '" + username + "' NÃO foi encontrado na consulta ao banco de dados.");
            throw new UsernameNotFoundException("Usuário não encontrado com o login: " + username);
        }
    }
}
