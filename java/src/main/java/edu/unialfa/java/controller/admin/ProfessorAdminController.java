package edu.unialfa.java.controller.admin;

import edu.unialfa.java.model.Professor;
import edu.unialfa.java.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/professores")
public class ProfessorAdminController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("professores", professorService.listarTodos());
        return "admin/professores";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("professor", new Professor());
        return "admin/professor-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Professor professor) {
        professorService.salvar(professor);
        return "redirect:/admin/professores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("professor", professorService.buscarPorId(id));
        return "admin/professor-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        professorService.excluir(id);
        return "redirect:/admin/professores";
    }
}
