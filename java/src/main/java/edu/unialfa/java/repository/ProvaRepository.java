package edu.unialfa.java.repository;

import edu.unialfa.java.model.Prova;
import edu.unialfa.java.model.TurmaDisciplina;
import edu.unialfa.java.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import edu.unialfa.java.model.Turma;
import edu.unialfa.java.model.Disciplina;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProvaRepository extends JpaRepository<Prova, Long> {

    @Query("SELECT p FROM Prova p " +
            "WHERE p.turmaDisciplina = :td " +
            "AND p.bimestre = :bimestre " +
            "AND p.turmaDisciplina.turma.anoLetivo = :anoLetivo")
    Optional<Prova> findByTurmaDisciplinaAndBimestreAndAnoLetivo(
            @Param("td") TurmaDisciplina turmaDisciplina,
            @Param("bimestre") Integer bimestre,
            @Param("anoLetivo") Integer anoLetivo);


    Optional<Prova> findByTurmaDisciplinaAndBimestreAndDataAplicacao(
            TurmaDisciplina td, Integer bimestre, LocalDate dataAplicacao);

    @Query("SELECT p FROM Prova p LEFT JOIN FETCH p.questoes WHERE p.id = :id")
    Optional<Prova> buscarPorIdComQuestoes(@Param("id") Long id);

    List<Prova> findByTurmaDisciplina_Professor(Professor professor);

    List<Prova> findByTurmaDisciplinaIdAndBimestre(Long turmaDisciplinaId, Integer bimestre);

    List<Prova> findByTurmaDisciplinaIdInAndBimestre(List<Long> turmaDisciplinaIds, int bimestre);

}
