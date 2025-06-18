package edu.unialfa.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NotaPorDisciplinaDTO {
    private String disciplinaNome;
    private Integer bimestre;
    private Double nota;

    public NotaPorDisciplinaDTO(String disciplinaNome, Integer bimestre, Double nota) {
        this.disciplinaNome = disciplinaNome;
        this.bimestre = bimestre;
        this.nota = nota;
    }
}
