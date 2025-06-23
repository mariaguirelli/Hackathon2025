package edu.unialfa.java.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProvaDTO {
    private Long id;
    private String nome;
    private LocalDate data;
    private int bimestre;
}