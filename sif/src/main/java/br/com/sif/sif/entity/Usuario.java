package br.com.sif.sif.entity;

import br.com.sif.sif.entity.enums.Perfil;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil;

    // Um usuário (atendente) pode realizar várias retiradas
    @OneToMany(mappedBy = "atendente")
    private Set<Retirada> retiradasRealizadas;

    // --- Métodos da Interface UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define o papel/perfil do usuário para o Spring Security
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.perfil.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        // O "username" para o Spring Security será o nosso campo "login"
        return this.login;
    }

    // Para simplificar, vamos retornar true para os métodos abaixo
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
