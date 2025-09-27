package br.com.sif.sif.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "processo_medicamentos")
@Getter
@Setter
@EqualsAndHashCode(exclude = "processo") // Excluímos o relacionamento de volta para Processo
public class ItemProcesso {
    // ... todos os seus campos continuam os mesmos ...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "processo_id")
    @JsonBackReference("processo-itens") // Use o mesmo nome do "pai"
    private Processo processo;
    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;
}