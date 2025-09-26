package br.com.sif.sif.dto;

import java.util.List;

import br.com.sif.sif.entity.Processo;

public record ProcessoRequestDTO( Long pacienteId,
        ProcessoDetalheDTO processo,
        List<ItemProcessoDTO> itens) {
}
