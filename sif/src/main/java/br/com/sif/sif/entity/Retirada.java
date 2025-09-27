package br.com.sif.sif.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"processo", "atendente"})
public class Retirada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "processo_id", nullable = false)
    @JsonBackReference("processo-retiradas") // Use o mesmo nome do "pai"
    private Processo processo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuario-retiradas") // Use o mesmo nome do "pai"
    private Usuario atendente;

    private LocalDate dataRetirada;
    private String nomeRetirou;
    private Integer quantidadeDispensada; // Ajustado para Integer para representar unidades
}
