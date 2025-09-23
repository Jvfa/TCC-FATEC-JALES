package br.com.sif.sif.dto;

// Este DTO representa a entrada de dados para um item do processo:
// o ID do medicamento e a quantidade necessária.
public record ItemProcessoDTO(Long medicamentoId, Integer quantidade) {
    
}
