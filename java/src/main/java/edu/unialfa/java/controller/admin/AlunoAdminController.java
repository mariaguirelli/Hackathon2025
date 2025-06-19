package edu.unialfa.java.controller.admin;

import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/alunos")
public class AlunoAdminController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        return "admin/alunos";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "admin/aluno-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Aluno aluno) {
        alunoService.salvar(aluno);
        return "redirect:/admin/alunos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("aluno", alunoService.buscarPorId(id));
        return "admin/aluno-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        alunoService.excluir(id);
        return "redirect:/admin/alunos";
    }
}
