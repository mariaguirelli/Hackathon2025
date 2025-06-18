package edu.unialfa.java.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "turmas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer anoLetivo;
}
