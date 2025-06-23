package edu.unialfa.java.api;

import edu.unialfa.java.api.dto.ProvaDTO;
import edu.unialfa.java.api.service.ProvaApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provas")
public class ProvaApiController {

    private final ProvaApiService provaService;

    public ProvaApiController(ProvaApiService provaService) {
        this.provaService = provaService;
    }

    // Endpoint para buscar provas do aluno por bimestre
    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<ProvaDTO>> getProvasDoAlunoPorBimestre(
            @PathVariable Long alunoId,
            @RequestParam int bimestre) {

        List<ProvaDTO> provas = provaService.buscarProvasDoAlunoPorBimestre(alunoId, bimestre);
        return ResponseEntity.ok(provas);
    }
}

