package edu.unialfa.java.api;

import edu.unialfa.java.api.service.QuestaoApiService;
import edu.unialfa.java.model.Questao;
import edu.unialfa.java.service.QuestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questoes")
public class QuestaoApiController {

    @Autowired
    private QuestaoApiService questaoApiService;

    @GetMapping("/prova/{provaId}")
    public ResponseEntity<List<Questao>> listarQuestoesPorProva(@PathVariable Long provaId) {
        List<Questao> questoes = questaoApiService.listarQuestoesPorProvaId(provaId);
        return ResponseEntity.ok(questoes);
    }
}
