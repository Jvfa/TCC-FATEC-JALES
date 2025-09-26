package br.com.sif.sif.config;

import br.com.sif.sif.config.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // IMPORTANTE: Importe o HttpMethod
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // --- Regras Públicas ---
                    req.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();

                    // --- Regras de Administrador / Farmacêutico ---
                    req.requestMatchers("/api/medicamentos/**").hasAnyRole("ADMINISTRADOR", "FARMACEUTICO");

                    // --- Regras para QUALQUER usuário autenticado ---
                    // Permite que qualquer usuário logado crie, edite ou delete pacientes e processos.
                    req.requestMatchers(HttpMethod.POST, "/api/pacientes", "/api/processos").authenticated();
                    req.requestMatchers(HttpMethod.PUT, "/api/pacientes/**").authenticated();
                    
                    // Permite que qualquer usuário logado veja os dados (requisições GET)
                    // Esta regra agora cobre explicitamente os GETs que estavam falhando.
                    req.requestMatchers(HttpMethod.GET, "/api/pacientes/**", "/api/processos/**").authenticated();

                    // Qualquer outra requisição que não foi mencionada acima precisa de autenticação.
                    // Isso serve como uma regra de segurança "pega-tudo".
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}