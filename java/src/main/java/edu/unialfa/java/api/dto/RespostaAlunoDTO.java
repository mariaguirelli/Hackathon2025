package edu.unialfa.java.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaAlunoDTO {
    private Long alunoId;
    private Long questaoId;
    private String respostaMarcada;
}
