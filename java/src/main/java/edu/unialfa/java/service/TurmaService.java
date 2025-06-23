package edu.unialfa.java.service;

import edu.unialfa.java.api.dto.AlunoDTO;
import edu.unialfa.java.api.dto.TurmaDTO;
import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.model.Turma;
import edu.unialfa.java.model.TurmaAluno;
import edu.unialfa.java.model.Usuario;
import edu.unialfa.java.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Turma> listarTurmasPorAluno(Long alunoId) {
        return turmaRepository.findTurmasByAlunoId(alunoId);
    }
}
