package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.RespostaAluno;

import java.util.List;

public interface RespostaAlunoRepository extends JpaRepository<RespostaAluno, Long> {
    List<RespostaAluno> findByAlunoId(Long alunoId);

    List<RespostaAluno> findByQuestaoId(Long questaoId);

    List<RespostaAluno> findByAlunoIdAndQuestaoProvaId(Long alunoId, Long provaId);

    List<RespostaAluno> findByQuestaoProvaId(Long provaId);
}
