package br.com.sif.sif;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner; // Importe
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.security.crypto.password.PasswordEncoder; // Importe

@SpringBootApplication
@AutoConfigureAfter(FlywayAutoConfiguration.class)
public class SifApplication { // Adicione "implements CommandLineRunner"

    @Autowired
    private PasswordEncoder passwordEncoder; // Injete o codificador de senhas

    public static void main(String[] args) {
        SpringApplication.run(SifApplication.class, args);
    }


}
