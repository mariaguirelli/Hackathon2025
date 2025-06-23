package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.TurmaDisciplina;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurmaDisciplinaRepository extends JpaRepository<TurmaDisciplina, Long> {

    List<TurmaDisciplina> findByTurmaId(Long turmaId);

    List<TurmaDisciplina> findByProfessorId(Long professorId);

    boolean existsByProfessorId(Long professorId);

    boolean existsByDisciplinaId(Long disciplinaId);

    boolean existsByTurmaId(Long turmaId);

    List<TurmaDisciplina> findByTurmaIdIn(List<Long> turmaIds);

    @Query("SELECT DISTINCT td.turma.id FROM TurmaDisciplina td WHERE td.professor.id = :professorId")
    List<Long> findTurmaIdsByProfessorId(@Param("professorId") Long professorId);

}
