package br.com.sif.sif.entity;

import java.time.LocalDate;
import java.util.Set;

import br.com.sif.sif.entity.enums.StatusProcesso;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "processos")
@Data
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    private LocalDate dataAbertura;
    private Integer mesInicioValidade;
    private Integer mesFimValidade;
    private String cid;

    @Enumerated(EnumType.STRING)
    private StatusProcesso status;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    // Um processo tem vários medicamentos (através da tabela de junção)
    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL)
    private Set<ItemProcesso> itensMedicamentos;

    // Um processo tem várias retiradas
    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL)
    private Set<Retirada> retiradas;
}
