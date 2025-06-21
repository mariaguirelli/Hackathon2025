package edu.unialfa.java.controller.admin;

import edu.unialfa.java.model.*;
import edu.unialfa.java.repository.AlunoRepository;
import edu.unialfa.java.repository.ProfessorRepository;
import edu.unialfa.java.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor

public class UsuarioAdminController {

    private final UsuarioService usuarioService;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;

    @GetMapping
    public String listar(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // ou ((User) auth.getPrincipal()).getUsername()

        model.addAttribute("usuarios", usuarioService.listarTodosExceto(email));
        return "admin/usuarios/index";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("alunos", alunoRepository.findAll());
        model.addAttribute("professores", professorRepository.findAll());
        model.addAttribute("perfis", Perfil.values());
        return "admin/usuarios/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.salvar(usuario);
            redirectAttributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage()); // exemplo: "E-mail já está em uso"
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar usuário: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }

        return "redirect:/admin/usuarios";
    }


    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("alunos", alunoRepository.findAll());
        model.addAttribute("professores", professorRepository.findAll());
        model.addAttribute("perfis", Perfil.values());
        return "admin/usuarios/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir usuário: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/admin/usuarios";
    }

}
