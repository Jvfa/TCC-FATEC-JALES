package br.com.sif.sif.dto;

import java.util.List;

import br.com.sif.sif.entity.Processo;

public record ProcessoRequestDTO( Long pacienteId, Processo processo, // Contém dataAbertura, dataValidade, cid, observacoes 
    List<ItemProcessoDTO> itens) {
}
