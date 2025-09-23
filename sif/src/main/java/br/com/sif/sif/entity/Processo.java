package br.com.sif.sif.entity;

import java.time.LocalDate;
import java.util.Set;

import br.com.sif.sif.entity.enums.StatusProcesso;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private LocalDate dataValidade;
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
