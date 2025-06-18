package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.TurmaAluno;

import java.util.List;

public interface TurmaAlunoRepository extends JpaRepository<TurmaAluno, Long> {
    List<TurmaAluno> findByAlunoId(Long alunoId);
    List<TurmaAluno> findByTurmaId(Long turmaId);
}
