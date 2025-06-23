package edu.unialfa.java.api;

import edu.unialfa.java.api.dto.AlunoDTO;
import edu.unialfa.java.api.dto.TurmaDTO;
import edu.unialfa.java.api.service.TurmaApiService;
import edu.unialfa.java.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaApiController {

    @Autowired
    private TurmaApiService turmaApiService;

    @GetMapping("/por-professor")
    public ResponseEntity<List<TurmaDTO>> listarTurmasDoProfessor(@RequestParam String email) {
        List<TurmaDTO> turmas = turmaApiService.buscarTurmasDoProfessorPorEmail(email);
        return ResponseEntity.ok(turmas);
    }
}
