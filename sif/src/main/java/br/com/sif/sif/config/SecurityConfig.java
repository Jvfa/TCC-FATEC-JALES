package br.com.sif.sif.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * A anotação @Configuration indica ao Spring que esta é uma classe
 * que contém definições de Beans e outras configurações da aplicação.
 */
@Configuration
public class SecurityConfig {
    /**
     * A anotação @Bean diz ao Spring: "Execute este método e o objeto
     * que ele retorna será um Bean gerenciado por você".
     *
     * O nome do método (passwordEncoder) se torna o nome padrão do Bean.
     *
     * @return Uma instância de BCryptPasswordEncoder, que é uma implementação
     * forte e segura da interface PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
