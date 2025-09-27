package br.com.sif.sif.dto;

import java.util.List;

public record ProcessoRequestDTO( Long pacienteId,
        ProcessoDetalheDTO processo,
        List<ItemProcessoDTO> itens) {
}
