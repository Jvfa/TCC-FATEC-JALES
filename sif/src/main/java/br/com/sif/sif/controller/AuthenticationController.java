package br.com.sif.sif.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sif.sif.dto.LoginRequestDTO;
import br.com.sif.sif.dto.LoginResponseDTO;
import br.com.sif.sif.entity.Usuario;
import br.com.sif.sif.service.TokenService;



@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService; // INJETAR

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) { // ATUALIZAR CONSTRUTOR
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService; // ATUALIZAR CONSTRUTOR
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        // GERAR O TOKEN REAL
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
