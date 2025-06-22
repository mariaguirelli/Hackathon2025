package edu.unialfa.java.repository;

import edu.unialfa.java.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.Disciplina;

import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByAlunoId(Long alunoId);
}
