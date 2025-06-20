package edu.unialfa.java.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "disciplinas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private int cargaHoraria;
}
