package edu.unialfa.java.controller.admin;

import edu.unialfa.java.model.Turma;
import edu.unialfa.java.service.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/turmas")
@RequiredArgsConstructor
public class TurmaAdminController {

    private final TurmaService turmaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("turmas", turmaService.listarTodas());
        return "admin/turmas/index";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("turma", new Turma());
        return "admin/turmas/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Turma turma = turmaService.buscarPorId(id)
                .orElse(null);

        if (turma == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Turma não encontrada.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
            return "redirect:/admin/turmas";
        }

        model.addAttribute("turma", turma);
        return "admin/turmas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Turma turma, RedirectAttributes redirectAttributes) {
        try {
            turmaService.salvar(turma);
            redirectAttributes.addFlashAttribute("mensagem", "Turma salva com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar turma: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/turmas";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            turmaService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Turma excluída com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir turma: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/turmas";
    }
}
