package br.com.sif.sif.dto;

public record RetiradaRequestDTO(Long processoId,
    Long atendenteId,
    String nomeRetirou,
    Integer quantidade) {}
