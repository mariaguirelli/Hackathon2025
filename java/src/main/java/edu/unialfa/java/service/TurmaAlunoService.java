package edu.unialfa.java.service;

import edu.unialfa.java.model.TurmaAluno;
import edu.unialfa.java.repository.TurmaAlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TurmaAlunoService {

    private final TurmaAlunoRepository repository;

    public List<TurmaAluno> listarTodos() {
        return repository.findAll();
    }

    public void salvar(TurmaAluno turmaAluno) {
        repository.save(turmaAluno);
    }

    public Optional<TurmaAluno> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
