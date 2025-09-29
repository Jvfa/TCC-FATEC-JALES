package br.com.sif.sif.dto;

public record RetiradaRequestDTO(
    Long processoId,
    String nomeRetirou,
    Integer quantidade
) {}
