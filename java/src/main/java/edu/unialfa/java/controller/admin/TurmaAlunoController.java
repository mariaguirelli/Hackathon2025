package edu.unialfa.java.controller.admin;

import edu.unialfa.java.model.*;
import edu.unialfa.java.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/turma-alunos")
@RequiredArgsConstructor
public class TurmaAlunoController {

    private final TurmaService turmaService;
    private final AlunoService alunoService;
    private final TurmaAlunoService turmaAlunoService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("lista", turmaAlunoService.listarTodos());
        return "admin/turmaAluno/index";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("turmaAluno", new TurmaAluno());
        model.addAttribute("turmas", turmaService.listarTodas());
        model.addAttribute("alunos", alunoService.listarTodos());
        return "admin/turmaAluno/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var turmaAluno = turmaAlunoService.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("turmaAluno", turmaAluno);
        model.addAttribute("turmas", turmaService.listarTodas());
        model.addAttribute("alunos", alunoService.listarTodos());
        return "admin/turmaAluno/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute TurmaAluno turmaAluno, RedirectAttributes redirectAttributes) {
        try {
            turmaAlunoService.salvar(turmaAluno);
            redirectAttributes.addFlashAttribute("mensagem", "Vínculo salvo com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar vínculo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/turma-alunos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            turmaAlunoService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Vínculo excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/turma-alunos";
    }
}
