package edu.unialfa.java.repository;

import edu.unialfa.java.dto.NotaPorDisciplinaDTO;
import edu.unialfa.java.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByAlunoId(Long alunoId);
    List<Nota> findByProvaId(Long provaId);
    List<Nota> findByProvaIdIn(List<Long> provaIds);


    @Query("""
        SELECT new edu.unialfa.java.dto.NotaPorDisciplinaDTO(
            td.disciplina.nome, p.bimestre, n.nota)
        FROM Nota n
        JOIN n.prova p
        JOIN p.turmaDisciplina td
        WHERE n.aluno.id = :alunoId
        ORDER BY p.bimestre
    """)
    List<NotaPorDisciplinaDTO> findNotasPorAlunoOrganizadasPorBimestre(@Param("alunoId") Long alunoId);
}
