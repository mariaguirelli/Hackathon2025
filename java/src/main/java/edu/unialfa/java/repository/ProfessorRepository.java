package edu.unialfa.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
