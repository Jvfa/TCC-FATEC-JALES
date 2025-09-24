package br.com.sif.sif.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * A anotação @Configuration indica ao Spring que esta é uma classe
 * que contém definições de Beans e outras configurações da aplicação.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * A anotação @Bean diz ao Spring: "Execute este método e o objeto
     * que ele retorna será um Bean gerenciado por você".
     *
     * O nome do método (passwordEncoder) se torna o nome padrão do Bean.
     *
     * @return Uma instância de BCryptPasswordEncoder, que é uma implementação
     *         forte e segura da interface PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF pois usaremos token
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sessão stateless
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/api/auth/login").permitAll(); // Permite acesso ao endpoint de login
                    req.anyRequest().authenticated(); // Exige autenticação para todas as outras rotas
                })
                .build();
    }
}
