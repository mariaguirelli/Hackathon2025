package edu.unialfa.java.api;

import edu.unialfa.java.api.dto.AlunoDTO;
import edu.unialfa.java.api.service.AlunoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/aluno")
public class AlunoApiController {

    @Autowired
    private AlunoApiService alunoApiService;

    @GetMapping("/{turmaId}/alunos")
    public ResponseEntity<List<AlunoDTO>> listarAlunosDaTurma(@PathVariable Long turmaId) {
        List<AlunoDTO> alunos = alunoApiService.buscarAlunosDaTurma(turmaId);
        return ResponseEntity.ok(alunos);
    }
}

