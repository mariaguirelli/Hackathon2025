package edu.unialfa.java.controller.admin;

import edu.unialfa.java.model.Turma;
import edu.unialfa.java.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/turmas")
public class TurmaAdminController {

    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("turmas", turmaService.listarTodas());
        return "admin/turmas";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("turma", new Turma());
        return "admin/turma-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Turma turma) {
        turmaService.salvar(turma);
        return "redirect:/admin/turmas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("turma", turmaService.buscarPorId(id));
        return "admin/turma-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        turmaService.excluir(id);
        return "redirect:/admin/turmas";
    }
}
