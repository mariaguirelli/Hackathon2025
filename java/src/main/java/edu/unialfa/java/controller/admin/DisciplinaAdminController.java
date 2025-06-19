package edu.unialfa.java.controller.admin;

import edu.unialfa.java.model.Disciplina;
import edu.unialfa.java.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/disciplinas")

public class DisciplinaAdminController {

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        return "admin/disciplinas";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "admin/disciplina-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Disciplina disciplina) {
        disciplinaService.salvar(disciplina);
        return "redirect:/admin/disciplinas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("disciplina", disciplinaService.buscarPorId(id));
        return "admin/disciplina-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        disciplinaService.excluir(id);
        return "redirect:/admin/disciplinas";
    }
}
