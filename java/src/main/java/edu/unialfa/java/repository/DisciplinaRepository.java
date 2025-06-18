package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
}
