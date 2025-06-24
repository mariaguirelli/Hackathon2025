package edu.unialfa.java.service;

import edu.unialfa.java.model.TurmaDisciplina;
import edu.unialfa.java.repository.TurmaDisciplinaRepository;
import edu.unialfa.java.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurmaDisciplinaService {

    @Autowired
    TurmaDisciplinaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<TurmaDisciplina> listarTodos() {
        return repository.findAll();
    }

    public void salvar(TurmaDisciplina turmaDisciplina) {
        repository.save(turmaDisciplina);
    }

    public Optional<TurmaDisciplina> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public List<TurmaDisciplina> listarPorProfessorLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));

        if (usuario.getProfessor() == null) {
            throw new IllegalStateException("Usuário não está vinculado a um professor.");
        }

        Long professorId = usuario.getProfessor().getId();

        return repository.findByProfessorId(professorId);
    }


    private Long obterProfessorLogadoId() {
        // Implementar pegar o ID do professor logado pelo SecurityContext
        return 1L; // placeholder
    }
}
