package edu.unialfa.java.controller.aluno;

import edu.unialfa.java.dto.NotaPorDisciplinaDTO;
import edu.unialfa.java.model.Turma;
import edu.unialfa.java.repository.TurmaRepository;
import edu.unialfa.java.repository.UsuarioRepository;
import edu.unialfa.java.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/aluno/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public String index() {
        // Redireciona para a seleção de notas
        return "redirect:/aluno/notas/selecionar";
    }

    private Long getAlunoIdLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .map(usuario -> {
                    if (usuario.getAluno() == null) {
                        throw new RuntimeException("Usuário logado não é um aluno.");
                    }
                    return usuario.getAluno().getId();
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    @GetMapping("/selecionar")
    public String selecionarNotas(Model model) {
        Long alunoId = getAlunoIdLogado();
        List<Turma> turmas = turmaRepository.findTurmasByAlunoId(alunoId);

        model.addAttribute("turmas", turmas);
        model.addAttribute("bimestres", List.of(1, 2, 3, 4));

        return "aluno/notas"; // nome do HTML
    }

    @GetMapping("/listar")
    public String listarNotas(
            @RequestParam Long turmaId,
            @RequestParam Integer bimestre,
            Model model) {

        Long alunoId = getAlunoIdLogado();
        List<NotaPorDisciplinaDTO> notas = notaService.listarNotas(alunoId, turmaId, bimestre);

        // Recarrega os selects
        List<Turma> turmas = turmaRepository.findTurmasByAlunoId(alunoId);
        model.addAttribute("turmas", turmas);
        model.addAttribute("bimestres", List.of(1, 2, 3, 4));
        model.addAttribute("notas", notas);

        return "aluno/notas";
    }

}
