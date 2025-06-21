package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.TurmaAluno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TurmaAlunoRepository extends JpaRepository<TurmaAluno, Long> {
    List<TurmaAluno> findByAlunoId(Long alunoId);
    List<TurmaAluno> findByTurmaId(Long turmaId);

    @Query("SELECT ta FROM TurmaAluno ta WHERE ta.turma.id = :turmaId AND ta.aluno.id = :alunoId")
    Optional<TurmaAluno> findByTurmaIdAndAlunoId(@Param("turmaId") Long turmaId, @Param("alunoId") Long alunoId);
}
