package br.com.sif.sif.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sif.sif.dto.LoginRequestDTO;
import br.com.sif.sif.dto.LoginResponseDTO;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());

        // O Spring Security faz a validação do usuário e senha
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        // Se a autenticação for bem-sucedida, aqui você geraria o token JWT.
        // Por simplicidade, vamos retornar um token "fake". Em um projeto real,
        // você usaria uma biblioteca como a JJWT para criar um token seguro.
        String token = "TOKEN_JWT_GERADO_AQUI_PARA_O_USUARIO_" + auth.getName();

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
