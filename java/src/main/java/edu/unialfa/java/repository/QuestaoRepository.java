package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.Questao;

import java.util.List;

public interface QuestaoRepository extends JpaRepository<Questao, Long> {
    List<Questao> findByProvaId(Long provaId);
}
