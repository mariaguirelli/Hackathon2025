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
        Optional<TurmaAluno> existente = repository.findByTurmaIdAndAlunoId(
                turmaAluno.getTurma().getId(),
                turmaAluno.getAluno().getId()
        );

        // Se for inclusão nova (sem ID) ou edição mudando turma/aluno
        if (existente.isPresent() && (turmaAluno.getId() == null || !existente.get().getId().equals(turmaAluno.getId()))) {
            throw new IllegalStateException("Este aluno já está vinculado a esta turma.");
        }

        repository.save(turmaAluno);
    }


    public Optional<TurmaAluno> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
