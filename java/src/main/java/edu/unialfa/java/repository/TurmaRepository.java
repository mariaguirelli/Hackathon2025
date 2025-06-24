package edu.unialfa.java.repository;

import edu.unialfa.java.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Query("SELECT ta.turma FROM TurmaAluno ta WHERE ta.aluno.id = :alunoId")
    List<Turma> findTurmasByAlunoId(@Param("alunoId") Long alunoId);

    @Query("""
    SELECT td.turma FROM TurmaDisciplina td
    WHERE td.professor.id IN (
        SELECT u.professor.id FROM Usuario u WHERE u.email = :email
    )
""")
    List<Turma> findTurmasByProfessorEmail(@Param("email") String email);

    @Query("""
    SELECT td.turma FROM TurmaDisciplina td
    WHERE td.professor.id IN (
        SELECT u.professor.id FROM Usuario u WHERE u.email = :email
    )
    AND td.turma.anoLetivo = :anoLetivo
""")
    List<Turma> findTurmasByProfessorEmailAndAnoLetivo(@Param("email") String email, @Param("anoLetivo") Integer anoLetivo);

}

