package br.com.sif.sif.dto;

import java.time.LocalDate;

public record PacienteUpdateDTO(
    String nome,
    String nomeMae,
    LocalDate dataNascimento,
    String rg,
    String cns,
    String cep,
    String cidade,
    String bairro,
    String rua,
    String numero,
    String complemento,
    String telefone,
    Double peso,
    Double altura,
    String cor
) {}
