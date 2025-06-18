package edu.unialfa.java.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "turma_aluno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurmaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;
}
