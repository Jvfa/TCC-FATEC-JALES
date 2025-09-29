package br.com.sif.sif.entity;

import br.com.sif.sif.entity.enums.StatusProcesso;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "processos")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"paciente", "itensMedicamentos", "retiradas"}) // Excluímos todos os relacionamentos
public class Processo {
    // ... todos os seus campos continuam os mesmos ...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer mesInicioValidade;
    private Integer mesFimValidade;
    private LocalDate dataAbertura;
    private String cid;
    @Enumerated(EnumType.STRING)
    private StatusProcesso status;
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @JsonBackReference("paciente-processos") // Use o mesmo nome do "pai"
    private Paciente paciente;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL)
    @JsonManagedReference("processo-itens") // Novo nome para esta relação
    private Set<ItemProcesso> itensMedicamentos;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL)
    @JsonManagedReference("processo-retiradas") // Novo nome para esta relação
    private Set<Retirada> retiradas;

    @JsonProperty("pacienteId")
    public Long getPacienteId() {
        return paciente != null ? paciente.getId() : null;
    }
}
