package edu.unialfa.java.api;

import edu.unialfa.java.api.dto.RespostaAlunoDTO;
import edu.unialfa.java.api.service.RespostaAlunoService;
import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.model.Questao;
import edu.unialfa.java.model.RespostaAluno;
import edu.unialfa.java.repository.AlunoRepository;
import edu.unialfa.java.repository.QuestaoRepository;
import edu.unialfa.java.repository.RespostaAlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/respostas")
@RequiredArgsConstructor
public class RespostaAlunoApiController {

    private final RespostaAlunoRepository respostaAlunoRepository;
    private final AlunoRepository alunoRepository;
    private final QuestaoRepository questaoRepository;
    private final RespostaAlunoService respostaAlunoService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarRespostas(@RequestBody List<RespostaAlunoDTO> respostasDto) {
        if (respostasDto == null || respostasDto.isEmpty()) {
            return ResponseEntity.badRequest().body("Lista de respostas vazia ou nula.");
        }

        try {
            respostaAlunoService.salvarRespostasERegistrarProva(respostasDto);
            return ResponseEntity.ok("Respostas salvas com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Erro ao salvar respostas: " + e.getMessage());
        }
    }

}
