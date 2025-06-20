package edu.unialfa.java.controller.admin;

import edu.unialfa.java.model.*;
import edu.unialfa.java.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/turma-disciplinas")
@RequiredArgsConstructor
public class TurmaDisciplinaController {

    private final TurmaDisciplinaService turmaDisciplinaService;
    private final TurmaService turmaService;
    private final DisciplinaService disciplinaService;
    private final ProfessorService professorService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("lista", turmaDisciplinaService.listarTodos());
        return "admin/turmaDisciplina/index";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("turmaDisciplina", new TurmaDisciplina());
        model.addAttribute("turmas", turmaService.listarTodas());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("professores", professorService.listarTodos());
        return "admin/turmaDisciplina/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var turmaDisciplina = turmaDisciplinaService.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("turmaDisciplina", turmaDisciplina);
        model.addAttribute("turmas", turmaService.listarTodas());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("professores", professorService.listarTodos());
        return "admin/turmaDisciplina/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute TurmaDisciplina turmaDisciplina, RedirectAttributes redirectAttributes) {
        try {
            turmaDisciplinaService.salvar(turmaDisciplina);
            redirectAttributes.addFlashAttribute("mensagem", "Vínculo salvo com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar vínculo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/turma-disciplinas";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            turmaDisciplinaService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Vínculo excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/turma-disciplinas";
    }
}
