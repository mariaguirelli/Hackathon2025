package edu.unialfa.java.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurmaDTO {
    private Long id;
    private String nome;
    private Integer anoLetivo;
}
