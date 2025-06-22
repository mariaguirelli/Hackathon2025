package edu.unialfa.java.controller.admin;

import edu.unialfa.java.exception.CpfDuplicadoException;
import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.persistence.EntityNotFoundException;


@Controller
@RequestMapping("/admin/alunos")
public class AlunoAdminController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        return "admin/alunos/index";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "admin/alunos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Aluno aluno, RedirectAttributes redirectAttributes) {
        try {
            alunoService.salvar(aluno);
            redirectAttributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (CpfDuplicadoException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());  // Mensagem da data inválida aqui
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar aluno: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/alunos";
    }


    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Aluno aluno = alunoService.buscarPorId(id);
        if (aluno == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Aluno não encontrado.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
            return "redirect:/admin/alunos";
        }
        model.addAttribute("aluno", aluno);
        return "admin/alunos/form";
    }


    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            alunoService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Aluno excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Não foi possível excluir: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro inesperado: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/alunos";
    }


}
