package edu.unialfa.java.repository;

import edu.unialfa.java.model.Perfil;
import edu.unialfa.java.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByPerfil(Perfil perfil);

    // Método para buscar todos os usuários com aluno e professor carregados
    @Query("select u from Usuario u " +
            "left join fetch u.aluno " +
            "left join fetch u.professor")
    List<Usuario> findAllWithAlunoProfessor();

    // Opcional: buscar por id com join fetch
    @Query("select u from Usuario u " +
            "left join fetch u.aluno " +
            "left join fetch u.professor " +
            "where u.id = :id")
    Optional<Usuario> findByIdWithAlunoProfessor(@Param("id") Long id);
}
