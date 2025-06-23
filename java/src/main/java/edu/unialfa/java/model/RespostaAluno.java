package edu.unialfa.java.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "respostas_aluno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"aluno", "questao"})
@EqualsAndHashCode(exclude = {"aluno", "questao"})
public class RespostaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;

    @Column(name = "resposta_marcada", length = 1, nullable = false)
    private String respostaMarcada;  // Exemplo: "A", "B", "C", etc.

    @Column(name = "acertou")
    private Boolean acertou;

    @Column(name = "data_resposta", nullable = false)
    private LocalDateTime dataResposta;
}
