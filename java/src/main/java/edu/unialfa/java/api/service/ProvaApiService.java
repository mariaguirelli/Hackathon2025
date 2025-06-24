package edu.unialfa.java.api.service;

import edu.unialfa.java.api.dto.ProvaDTO;
import edu.unialfa.java.model.Prova;
import edu.unialfa.java.model.TurmaAluno;
import edu.unialfa.java.model.TurmaDisciplina;
import edu.unialfa.java.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import edu.unialfa.java.model.Usuario;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProvaApiService {

    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Autowired
    private TurmaAlunoRepository turmaAlunoRepository;

    @Autowired
    private ProvaAlunoRepository provaAlunoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<ProvaDTO> buscarProvasDoAlunoPorBimestre(Long alunoId, int bimestre) {
        // Obter email do usuário logado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar usuário logado
        Usuario usuarioLogado = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));

        if (usuarioLogado.getProfessor() == null) {
            throw new IllegalStateException("Usuário logado não está associado a um professor");
        }

        Long professorId = usuarioLogado.getProfessor().getId();

        // 1. Buscar turmas do aluno
        List<TurmaAluno> turmasDoAluno = turmaAlunoRepository.findByAlunoId(alunoId);
        List<Long> turmaIds = turmasDoAluno.stream()
                .map(ta -> ta.getTurma().getId())
                .collect(Collectors.toList());

        if(turmaIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. Buscar TurmaDisciplina pelas turmas e pelo professor logado
        List<TurmaDisciplina> turmaDisciplinas = turmaDisciplinaRepository.findByTurmaIdInAndProfessorId(turmaIds, professorId);
        List<Long> turmaDisciplinaIds = turmaDisciplinas.stream()
                .map(TurmaDisciplina::getId)
                .collect(Collectors.toList());

        if(turmaDisciplinaIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 3. Buscar todas as provas daquele bimestre nas turmas do aluno e professor
        List<Prova> provas = provaRepository.findByTurmaDisciplinaIdInAndBimestre(turmaDisciplinaIds, bimestre);

        if (provas.isEmpty()) {
            return Collections.emptyList();
        }

        // 4. Buscar IDs das provas já feitas pelo aluno
        List<Long> provasFeitasIds = provaAlunoRepository.findProvaIdsByAlunoId(alunoId);

        // 5. Filtrar apenas as que o aluno AINDA NÃO FEZ
        List<ProvaDTO> provasNaoFeitas = provas.stream()
                .filter(prova -> !provasFeitasIds.contains(prova.getId()))
                .map(prova -> new ProvaDTO(prova.getId(), prova.getTitulo(), prova.getDataAplicacao(), prova.getBimestre()))
                .collect(Collectors.toList());

        return provasNaoFeitas;
    }

}

