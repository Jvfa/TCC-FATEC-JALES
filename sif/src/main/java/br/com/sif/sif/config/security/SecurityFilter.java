package br.com.sif.sif.config.security;

import br.com.sif.sif.repository.UsuarioRepository;
import br.com.sif.sif.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("--- INICIANDO SECURITY FILTER ---");
        var token = this.recoverToken(request);

        if (token != null) {
            System.out.println("Token encontrado: " + token);
            var login = tokenService.validateToken(token);
            System.out.println("Login extraído do token: " + login);


            if (login != null) {
                UserDetails user = usuarioRepository.findByLogin(login).orElse(null);

                if (user != null) {
                    System.out.println("Usuário encontrado no banco: " + user.getUsername());
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Usuário autenticado e adicionado ao contexto de segurança.");
                } else {
                    System.err.println("Usuário '" + login + "' do token não foi encontrado no banco de dados.");
                }
            }
        } else {
            System.out.println("Nenhum token encontrado no cabeçalho Authorization.");
        }

        filterChain.doFilter(request, response);
        System.out.println("--- FINALIZANDO SECURITY FILTER ---");
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
