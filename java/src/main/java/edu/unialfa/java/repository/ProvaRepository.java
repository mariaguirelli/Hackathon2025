package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.Prova;

import java.util.List;

public interface ProvaRepository extends JpaRepository<Prova, Long> {
    List<Prova> findByTurmaDisciplinaId(Long turmaDisciplinaId);
    List<Prova> findByBimestre(Integer bimestre);
    List<Prova> findByTurmaDisciplinaIdIn(List<Long> turmaDisciplinaIds);

    List<Prova> findByTurmaDisciplinaProfessorId(Long professorId);
}
