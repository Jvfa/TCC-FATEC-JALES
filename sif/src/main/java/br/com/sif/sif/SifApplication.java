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
public class SifApplication implements CommandLineRunner { // Adicione "implements CommandLineRunner"

    @Autowired
    private PasswordEncoder passwordEncoder; // Injete o codificador de senhas

    public static void main(String[] args) {
        SpringApplication.run(SifApplication.class, args);
    }

    // Adicione este método
    @Override
    public void run(String... args) throws Exception {
        // Este código irá rodar uma vez na inicialização
        String encodedPassword = passwordEncoder.encode("admin");
        System.out.println("\n\n\n========================= GERADOR DE HASH BCrypt =========================");
        System.out.println("O hash para a senha 'admin' neste ambiente é:");
        System.out.println(encodedPassword);
        System.out.println("COPIE A LINHA ACIMA E COLE NO SEU ARQUIVO V2__insert-default-admin-user.sql");
        System.out.println("==========================================================================\n\n\n");
    }
}
