package edu.unialfa.java.controller.admin;

import edu.unialfa.java.model.Disciplina;
import edu.unialfa.java.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/disciplinas")
@RequiredArgsConstructor
public class DisciplinaAdminController {

    private final DisciplinaService disciplinaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        return "admin/disciplinas/index";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "admin/disciplinas/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Disciplina disciplina = disciplinaService.buscarPorId(id)
                .orElse(null);

        if (disciplina == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Disciplina não encontrada.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
            return "redirect:/admin/disciplinas";
        }

        model.addAttribute("disciplina", disciplina);
        return "admin/disciplinas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Disciplina disciplina, RedirectAttributes redirectAttributes) {
        try {
            disciplinaService.salvar(disciplina);
            redirectAttributes.addFlashAttribute("mensagem", "Disciplina salva com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar disciplina: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/disciplinas";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            disciplinaService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Disciplina excluída com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir disciplina: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/disciplinas";
    }
}
