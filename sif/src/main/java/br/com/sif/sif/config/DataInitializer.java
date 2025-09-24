package br.com.sif.sif.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.sif.sif.entity.Usuario;
import br.com.sif.sif.entity.enums.Perfil;
import br.com.sif.sif.repository.UsuarioRepository;

import java.util.Arrays;

@Component // Anotação para que o Spring gerencie esta classe
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Injetamos os repositórios e serviços que precisamos
    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificamos se já existe algum usuário para não criar repetido
        if (usuarioRepository.count() == 0) {
            System.out.println("Nenhum usuário encontrado, criando usuário padrão...");

            // Criamos o usuário administrador
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setLogin("admin");
            // IMPORTANTE: A senha deve ser criptografada com o mesmo PasswordEncoder
            admin.setSenha(passwordEncoder.encode("admin"));
            admin.setPerfil(Perfil.ADMINISTRADOR);

            usuarioRepository.save(admin);

            System.out.println("Usuário 'admin' com senha 'admin' criado com sucesso!");
        } else {
            System.out.println("Usuários já existem no banco de dados.");
        }
    }
}
