package edu.unialfa.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class NotaPorDisciplinaDTO {
    private String disciplina;
    private Integer bimestre;
    private Double nota;
}
