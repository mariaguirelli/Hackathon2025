package edu.unialfa.java.api.service;

import edu.unialfa.java.api.dto.RespostaAlunoDTO;
import edu.unialfa.java.model.*;
import edu.unialfa.java.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RespostaAlunoService {

    @Autowired
    private RespostaAlunoRepository respostaRepo;

    @Autowired
    private ProvaAlunoRepository provaAlunoRepo;

    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private AlunoRepository alunoRepo;

    @Autowired
    private QuestaoRepository questaoRepo;

    @Autowired
    private NotaRepository notaRepository;

    @Transactional
    public void salvarRespostasERegistrarProva(List<RespostaAlunoDTO> respostasDTO) {
        if (respostasDTO.isEmpty()) return;

        Long alunoId = respostasDTO.get(0).getAlunoId();
        Long primeiraQuestaoId = respostasDTO.get(0).getQuestaoId();

        Aluno aluno = alunoRepo.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Questao primeiraQuestao = questaoRepo.findById(primeiraQuestaoId)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada"));

        Prova prova = primeiraQuestao.getProva();
        if (prova == null) {
            throw new RuntimeException("Prova não encontrada a partir da questão");
        }

        double somaNota = 0.0;

        for (RespostaAlunoDTO dto : respostasDTO) {
            Questao questao = questaoRepo.findById(dto.getQuestaoId())
                    .orElseThrow(() -> new RuntimeException("Questão não encontrada"));

            boolean acertou = dto.getRespostaMarcada().equalsIgnoreCase(questao.getRespostaCorreta());

            if (acertou) {
                somaNota += questao.getValor() != null ? questao.getValor() : 0.0;
            }

            RespostaAluno resposta = RespostaAluno.builder()
                    .aluno(aluno)
                    .questao(questao)
                    .respostaMarcada(dto.getRespostaMarcada())
                    .acertou(acertou)
                    .dataResposta(LocalDateTime.now())
                    .build();

            respostaRepo.save(resposta);
        }

        // Salva registro da prova do aluno
        ProvaAluno provaAluno = ProvaAluno.builder()
                .aluno(aluno)
                .prova(prova)
                .dataEnvio(LocalDateTime.now())
                .build();

        provaAlunoRepo.save(provaAluno);

        // Salva a nota do aluno na prova
        Nota nota = Nota.builder()
                .aluno(aluno)
                .prova(prova)
                .nota(somaNota)
                .build();

        notaRepository.save(nota);
    }
}

