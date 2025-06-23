package edu.unialfa.java.api.service;

import org.springframework.stereotype.Service;
import edu.unialfa.java.model.Questao;
import edu.unialfa.java.repository.QuestaoRepository;

import java.util.List;

@Service
public class QuestaoApiService {

    private final QuestaoRepository questaoRepository;

    public QuestaoApiService(QuestaoRepository questaoRepository) {
        this.questaoRepository = questaoRepository;
    }

    public List<Questao> listarQuestoesPorProvaId(Long provaId) {
        return questaoRepository.findByProvaId(provaId);
    }
}
