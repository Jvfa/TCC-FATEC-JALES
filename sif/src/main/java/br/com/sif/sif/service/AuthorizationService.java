package br.com.sif.sif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        UserDetails user = usuarioRepository.findByLogin(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o login: " + username));
        return user;
    }
}
