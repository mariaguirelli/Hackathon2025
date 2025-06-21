package edu.unialfa.java.repository;

import edu.unialfa.java.model.Prova;
import edu.unialfa.java.model.TurmaDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProvaRepository extends JpaRepository<Prova, Long> {
    // Busca prova por turmaDisciplina, bimestre e ano da dataAplicacao
    Optional<Prova> findByTurmaDisciplinaAndBimestreAndDataAplicacaoBetween(
            TurmaDisciplina turmaDisciplina, Integer bimestre, LocalDate start, LocalDate end);

    Optional<Prova> findByTurmaDisciplinaAndBimestreAndDataAplicacao(
            TurmaDisciplina td, Integer bimestre, LocalDate dataAplicacao);

    @Query("SELECT p FROM Prova p LEFT JOIN FETCH p.questoes WHERE p.id = :id")
    Optional<Prova> buscarPorIdComQuestoes(@Param("id") Long id);
}
