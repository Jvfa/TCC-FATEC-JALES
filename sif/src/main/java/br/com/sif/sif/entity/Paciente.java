package br.com.sif.sif.entity;

import java.time.LocalDate;

import br.com.sif.sif.entity.enums.StatusCadastro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;

@Entity
@Table(name = "pacientes")
@Data
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    
    private String nomeMae;
    private LocalDate dataNascimento;
    
    @Column(unique = true)
    private String cpf;
    private String rg;
    private String cns;
    private String endereco;
    private String telefone;
    private Double peso;
    private Double altura;
    private String cor;

    @Enumerated(EnumType.STRING)
    private StatusCadastro statusCadastro;

    // Um paciente pode ter vários processos
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Processo> processos;
}
