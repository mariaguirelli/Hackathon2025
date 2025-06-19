package edu.unialfa.java.controller.admin;

import edu.unialfa.java.model.Perfil;
import edu.unialfa.java.model.Usuario;
import edu.unialfa.java.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
@PreAuthorize("hasAuthority('ADMIN')")
public class UsuarioAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarAdmins(Model model) {
        model.addAttribute("usuarios", usuarioService.listarPorPerfil(Perfil.ADMIN));
        return "admin/usuarios/listar";
    }

    @GetMapping("/novo")
    public String novoAdmin(Model model) {
        Usuario usuario = new Usuario();
        usuario.setPerfil(Perfil.ADMIN); // força o perfil ADMIN
        model.addAttribute("usuario", usuario);
        return "admin/usuarios/formulario";
    }

    @PostMapping("/salvar")
    public String salvarAdmin(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setPerfil(Perfil.ADMIN); // garante que o perfil seja ADMIN
        usuarioService.salvar(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarAdmin(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorId(id));
        return "admin/usuarios/formulario";
    }

    @GetMapping("/deletar/{id}")
    public String deletarAdmin(@PathVariable Long id) {
        usuarioService.deletar(id);
        return "redirect:/admin/usuarios";
    }
}

