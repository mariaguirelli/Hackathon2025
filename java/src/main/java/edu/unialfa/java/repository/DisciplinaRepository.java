package edu.unialfa.java.repository;

import edu.unialfa.java.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.Disciplina;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    @Query("SELECT td.disciplina FROM TurmaDisciplina td JOIN TurmaAluno ta ON td.turma.id = ta.turma.id WHERE ta.aluno.id = :alunoId")
    List<Disciplina> findDisciplinasByAlunoId(@Param("alunoId") Long alunoId);
}
