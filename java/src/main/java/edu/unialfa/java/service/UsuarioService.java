package edu.unialfa.java.service;

import edu.unialfa.java.model.Perfil;
import edu.unialfa.java.model.Usuario;
import edu.unialfa.java.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarPorPerfil(Perfil perfil) {
        return usuarioRepository.findByPerfil(perfil);
    }

    public Usuario salvar(Usuario usuario) {
        if (!usuario.getSenha().startsWith("$2a$")) { // padrão de hash BCrypt
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}