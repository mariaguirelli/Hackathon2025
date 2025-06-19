package edu.unialfa.java.repository;

import edu.unialfa.java.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.unialfa.java.model.Aluno;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByRa(String ra);

    boolean existsByRa(String ra);

    Aluno findByCpf(String cpf);
}
