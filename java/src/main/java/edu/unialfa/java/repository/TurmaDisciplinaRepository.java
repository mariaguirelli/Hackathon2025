package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.TurmaDisciplina;

import java.util.List;

public interface TurmaDisciplinaRepository extends JpaRepository<TurmaDisciplina, Long> {

    List<TurmaDisciplina> findByTurmaId(Long turmaId);

    List<TurmaDisciplina> findByProfessorId(Long professorId);

    boolean existsByProfessorId(Long professorId);

    boolean existsByDisciplinaId(Long disciplinaId);

    boolean existsByTurmaId(Long turmaId);

}
