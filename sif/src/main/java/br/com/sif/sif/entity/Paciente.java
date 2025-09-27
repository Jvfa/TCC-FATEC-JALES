package br.com.sif.sif.entity;

import br.com.sif.sif.entity.enums.StatusCadastro;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@EqualsAndHashCode(exclude = "processos") // Excluímos 'processos' para evitar o loop
public class Paciente {
    // ... todos os seus campos continuam os mesmos ...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("paciente-processos") // Adicione um nome aqui
    private Set<Processo> processos;
}