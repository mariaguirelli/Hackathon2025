package edu.unialfa.java.seeders;

import edu.unialfa.java.model.Usuario;
import edu.unialfa.java.model.Perfil;
import edu.unialfa.java.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seed() {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@gmail.com");
            admin.setSenha(passwordEncoder.encode("admin@123"));
            admin.setPerfil(Perfil.ADMIN); // Use seu enum ou string

            usuarioRepository.save(admin);
        }
    }
}

