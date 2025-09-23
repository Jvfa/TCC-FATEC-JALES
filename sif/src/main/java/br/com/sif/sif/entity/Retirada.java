package br.com.sif.sif.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "retiradas")
@Data
public class Retirada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "processo_id", nullable = false)
    private Processo processo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario atendente;

    private LocalDate dataRetirada;
    private String nomeRetirou;
    private Integer quantidadeDispensada; // Ajustado para Integer para representar unidades
}
