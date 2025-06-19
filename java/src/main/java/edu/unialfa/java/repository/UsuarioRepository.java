package edu.unialfa.java.repository;

import edu.unialfa.java.model.Perfil;
import edu.unialfa.java.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByPerfil(Perfil perfil);
}
