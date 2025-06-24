package edu.unialfa.java.service;

import edu.unialfa.java.model.TurmaAluno;
import edu.unialfa.java.repository.AlunoRepository;
import edu.unialfa.java.repository.TurmaAlunoRepository;
import edu.unialfa.java.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TurmaAlunoService {

    @Autowired
    private TurmaAlunoRepository turmaAlunoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;


    public List<TurmaAluno> listarTodos() {
        return turmaAlunoRepository.findAll();
    }

    public void salvar(TurmaAluno turmaAluno) {
        Optional<TurmaAluno> existente = turmaAlunoRepository.findByTurmaIdAndAlunoId(
                turmaAluno.getTurma().getId(),
                turmaAluno.getAluno().getId()
        );

        // Se for inclusão nova (sem ID) ou edição mudando turma/aluno
        if (existente.isPresent() && (turmaAluno.getId() == null || !existente.get().getId().equals(turmaAluno.getId()))) {
            throw new IllegalStateException("Este aluno já está vinculado a esta turma.");
        }

        turmaAlunoRepository.save(turmaAluno);
    }

    public void vincularAlunosATurma(Long turmaId, List<Long> alunosIds) {
        var turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada"));

        for (Long alunoId : alunosIds) {
            var aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado: " + alunoId));

            boolean existe = turmaAlunoRepository.existsByTurmaIdAndAlunoId(turmaId, alunoId);
            if (existe) {
                throw new IllegalStateException("Esse aluno já está vinculado a esta turma.");
            }

            var vinculo = TurmaAluno.builder()
                    .turma(turma)
                    .aluno(aluno)
                    .build();
            turmaAlunoRepository.save(vinculo);
        }
    }

    public Optional<TurmaAluno> buscarPorId(Long id) {
        return turmaAlunoRepository.findById(id);
    }

    public void excluir(Long id) {
        turmaAlunoRepository.deleteById(id);
    }
}
