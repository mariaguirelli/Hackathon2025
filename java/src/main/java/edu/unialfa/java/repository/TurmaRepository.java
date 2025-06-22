package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.Turma;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Query("SELECT ta.turma FROM TurmaAluno ta WHERE ta.aluno.id = :alunoId")
    List<Turma> findTurmasByAlunoId(@Param("alunoId") Long alunoId);
}
