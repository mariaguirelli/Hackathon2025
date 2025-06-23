package edu.unialfa.java.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "provas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private LocalDate dataAplicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_disciplina_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TurmaDisciplina turmaDisciplina;


    private Integer bimestre;

    @OneToMany(mappedBy = "prova", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Builder.Default
    private List<Questao> questoes = new ArrayList<>();


    // Método auxiliar para adicionar questão e manter relação bidirecional
    public void adicionarQuestao(Questao questao) {
        questoes.add(questao);
        questao.setProva(this);
    }

    // Método auxiliar para remover questão e manter relação bidirecional
    public void removerQuestao(Questao questao) {
        questoes.remove(questao);
        questao.setProva(null);
    }
}
