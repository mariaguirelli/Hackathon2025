package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
}
