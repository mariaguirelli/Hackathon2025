package edu.unialfa.java.dto;

import lombok.Data;
import java.util.List;

@Data
public class TurmaAlunoDTO {
    private Long id;
    private Long turmaId;
    private List<Long> alunosIds;
}
