package edu.unialfa.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticaProvaDTO {
    private Double media;
    private Double notaMaxima;
    private Double notaMinima;
    private Long totalAlunos;
    private Long alunosAcimaDaMedia;
}
