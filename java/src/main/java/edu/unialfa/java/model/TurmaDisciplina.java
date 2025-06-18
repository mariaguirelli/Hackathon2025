package edu.unialfa.java.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "turma_disciplina")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurmaDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;
}
