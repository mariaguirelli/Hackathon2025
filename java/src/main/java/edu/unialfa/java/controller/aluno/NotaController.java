package edu.unialfa.java.controller.aluno;
import edu.unialfa.java.model.Nota;
import edu.unialfa.java.model.Disciplina;
import edu.unialfa.java.service.DisciplinaService;
import edu.unialfa.java.service.NotaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/aluno/notas")
public class NotaController {

    private final NotaService notaService;
    private final DisciplinaService disciplinaService;

    public NotaController(NotaService notaService, DisciplinaService disciplinaService) {
        this.notaService = notaService;
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public String listarNotas(HttpSession session, Model model) {
        Long alunoId = (Long) session.getAttribute("alunoId");

        List<Nota> notas = notaService.listarNotasPorAluno(alunoId);
        List<Disciplina> disciplinas = disciplinaService.listarDisciplinasPorAluno(alunoId);

        model.addAttribute("notas", notas);
        model.addAttribute("disciplinas", disciplinas);
        return "aluno/notas";
    }
}