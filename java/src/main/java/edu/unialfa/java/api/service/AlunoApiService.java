package edu.unialfa.java.api.service;

import edu.unialfa.java.api.dto.AlunoDTO;
import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.model.TurmaAluno;
import edu.unialfa.java.repository.AlunoRepository;
import edu.unialfa.java.repository.TurmaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoApiService {

    @Autowired
    private TurmaAlunoRepository turmaAlunoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public List<AlunoDTO> buscarAlunosDaTurma(Long turmaId) {
        List<TurmaAluno> turmaAlunos = turmaAlunoRepository.findByTurmaId(turmaId);

        List<Long> alunosIds = turmaAlunos.stream()
                .map(ta -> ta.getAluno().getId())
                .collect(Collectors.toList());

        List<Aluno> alunos = alunoRepository.findAllById(alunosIds);

        return alunos.stream()
                .map(aluno -> new AlunoDTO(
                        aluno.getId(),
                        aluno.getNome(),
                        aluno.getSobrenome(),
                        aluno.getRa(),
                        aluno.getCpf(),
                        aluno.getDataNascimento()
                ))
                .collect(Collectors.toList());

    }
}
