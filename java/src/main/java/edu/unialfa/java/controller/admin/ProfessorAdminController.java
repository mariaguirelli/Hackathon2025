package edu.unialfa.java.controller.admin;

import edu.unialfa.java.exception.CpfDuplicadoException;
import edu.unialfa.java.model.Professor;
import edu.unialfa.java.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/professores")
public class ProfessorAdminController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("professores", professorService.listarTodos());
        return "admin/professores/index";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("professor", new Professor());
        return "admin/professores/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Professor professor, RedirectAttributes redirectAttributes) {
        try {
            professorService.salvar(professor);
            redirectAttributes.addFlashAttribute("mensagem", "Professor salvo com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (CpfDuplicadoException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());  // Mensagem da data inválida aqui
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar professor: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/professores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Professor professor = professorService.buscarPorId(id);
        if (professor == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Professor não encontrado.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
            return "redirect:/admin/professores";
        }
        model.addAttribute("professor", professor);
        return "admin/professores/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            professorService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Professor excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir professor: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/professores";
    }

}
