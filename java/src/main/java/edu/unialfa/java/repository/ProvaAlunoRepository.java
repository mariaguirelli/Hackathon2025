package edu.unialfa.java.repository;

import edu.unialfa.java.model.ProvaAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvaAlunoRepository extends JpaRepository<ProvaAluno, Long> {
    @Query("SELECT pa.prova.id FROM ProvaAluno pa WHERE pa.aluno.id = :alunoId")
    List<Long> findProvaIdsByAlunoId(@Param("alunoId") Long alunoId);
}
