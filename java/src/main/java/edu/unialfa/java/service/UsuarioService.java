package edu.unialfa.java.service;

import edu.unialfa.java.model.Usuario;
import edu.unialfa.java.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;

    public List<Usuario> listarTodosExceto(String emailAtual) {
        return usuarioRepository.findAll()
                .stream()
                .filter(usuario -> !usuario.getEmail().equalsIgnoreCase(emailAtual))
                .toList();
    }

    public Usuario salvar(Usuario usuario) {
        // Valida e-mail duplicado (caso você já tenha feito)
        usuarioRepository.findByEmail(usuario.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(usuario.getId())) {
                throw new IllegalArgumentException("E-mail já está em uso por outro usuário.");
            }
        });

        // Valida aluno duplicado
        if (usuario.getAluno() != null) {
            usuarioRepository.findAll().forEach(existing -> {
                if (existing.getAluno() != null &&
                        existing.getAluno().getId().equals(usuario.getAluno().getId()) &&
                        !existing.getId().equals(usuario.getId())) {
                    throw new IllegalArgumentException("Este aluno já está vinculado a outro usuário.");
                }
            });
        }

        // Valida professor duplicado
        if (usuario.getProfessor() != null) {
            usuarioRepository.findAll().forEach(existing -> {
                if (existing.getProfessor() != null &&
                        existing.getProfessor().getId().equals(usuario.getProfessor().getId()) &&
                        !existing.getId().equals(usuario.getId())) {
                    throw new IllegalArgumentException("Este professor já está vinculado a outro usuário.");
                }
            });
        }

        if (usuario.getId() != null) {
            Usuario usuarioExistente = usuarioRepository.findById(usuario.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para edição"));

            if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
                // Mantém a senha antiga e mantém flag precisaAlterarSenha igual ao existente
                usuario.setSenha(usuarioExistente.getSenha());
                usuario.setPrecisaAlterarSenha(usuarioExistente.getPrecisaAlterarSenha());
            }
        }
        else {
            // Criação: define senha padrão conforme perfil e seta precisaAlterarSenha = true
            String senhaPadrao;
            switch (usuario.getPerfil()) {
                case PROFESSOR:
                    senhaPadrao = "prof@123";
                    break;
                case ALUNO:
                    senhaPadrao = "aluno@123";
                    break;
                case ADMIN:
                default:
                    senhaPadrao = "admin@123";
                    break;
            }
            usuario.setSenha(encoder.encode(senhaPadrao));
            usuario.setPrecisaAlterarSenha(true);
        }

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findByIdWithAlunoProfessor(id).orElse(null);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

}
