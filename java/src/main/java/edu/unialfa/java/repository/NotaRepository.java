package edu.unialfa.java.repository;

import edu.unialfa.java.dto.NotaPorDisciplinaDTO;
import edu.unialfa.java.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    @Query("SELECT new edu.unialfa.java.dto.NotaPorDisciplinaDTO(" +
            "d.nome, p.bimestre, n.nota) " +
            "FROM Nota n " +
            "JOIN n.prova p " +
            "JOIN p.turmaDisciplina td " +
            "JOIN td.disciplina d " +
            "JOIN TurmaAluno ta ON ta.aluno.id = n.aluno.id AND ta.turma.id = td.turma.id " +
            "WHERE n.aluno.id = :alunoId AND ta.turma.id = :turmaId AND p.bimestre = :bimestre")
    List<NotaPorDisciplinaDTO> buscarNotasPorTurmaEBimestre(@Param("alunoId") Long alunoId,
                                                            @Param("turmaId") Long turmaId,
                                                            @Param("bimestre") Integer bimestre);


    // Métodos já existentes que podem continuar
    List<Nota> findByAlunoId(Long alunoId);

    List<Nota> findByProvaId(Long provaId);

    boolean existsByProvaId(Long id);
}
