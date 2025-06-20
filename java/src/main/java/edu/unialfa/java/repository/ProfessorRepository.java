package edu.unialfa.java.repository;

import edu.unialfa.java.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.Professor;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByRegistro(String registro);

    boolean existsByRegistro(String registro);

    Professor findByCpf(String cpf);
}
