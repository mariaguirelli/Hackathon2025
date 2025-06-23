package edu.unialfa.java.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AlunoDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String ra;
    private String cpf;
    private LocalDate data_nascimento;
}