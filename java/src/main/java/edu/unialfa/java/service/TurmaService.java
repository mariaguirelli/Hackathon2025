package edu.unialfa.java.service;

import edu.unialfa.java.model.Turma;
import edu.unialfa.java.repository.TurmaAlunoRepository;
import edu.unialfa.java.repository.TurmaDisciplinaRepository;
import edu.unialfa.java.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurmaService {

    @Autowired

    private final TurmaRepository turmaRepository;

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Autowired
    private TurmaAlunoRepository turmaAlunoRepository;

    public List<Turma> listarTodas() {
        return turmaRepository.findAll();
    }

    public Optional<Turma> buscarPorId(Long id) {
        return turmaRepository.findById(id);
    }

    public void salvar(Turma turma) {
        turmaRepository.save(turma);
    }

    public void excluir(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new EntityNotFoundException("Turma não encontrada com o ID: " + id);
        }

        boolean turmaComAlunos = turmaAlunoRepository.existsByTurmaId(id);

        boolean turmaComDisciplinas = turmaDisciplinaRepository.existsByTurmaId(id);

        if (turmaComAlunos || turmaComDisciplinas) {
            throw new IllegalStateException("Não é possível excluir a turma. Existem alunos ou disciplinas vinculadas a ela.");
        }

        turmaRepository.deleteById(id);
    }

}
