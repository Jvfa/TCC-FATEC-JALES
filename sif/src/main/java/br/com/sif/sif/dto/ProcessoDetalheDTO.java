package br.com.sif.sif.dto;

import java.time.LocalDate;

public record ProcessoDetalheDTO(
        LocalDate dataAbertura,
        Integer mesInicioValidade,
        Integer mesFimValidade,
        String cid,
        String observacoes
) {}