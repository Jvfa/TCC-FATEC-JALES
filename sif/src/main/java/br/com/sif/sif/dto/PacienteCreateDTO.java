package br.com.sif.sif.dto;

import jakarta.validation.constraints.*; // Importe as anotações
import java.time.LocalDate;

public record PacienteCreateDTO(
    @NotBlank(message = "O nome não pode estar em branco")
    String nome,
    
    String nomeMae,
    
    @Past(message = "A data de nascimento deve ser no passado")
    LocalDate dataNascimento,
    
    @NotBlank(message = "O CPF não pode estar em branco")
    String cpf,

    String rg,
    String cns,
    String endereco,
    String telefone,
    
    @DecimalMin(value = "1.0", message = "O peso deve ser no mínimo 1.0 kg")
    @DecimalMax(value = "500.0", message = "O peso não pode exceder 500.0 kg")
    Double peso,
    
    @DecimalMin(value = "0.5", message = "A altura deve ser no mínimo 0.5 metros")
    @DecimalMax(value = "2.5", message = "A altura não pode exceder 2.5 metros")
    Double altura,
    
    String cor
) {}
