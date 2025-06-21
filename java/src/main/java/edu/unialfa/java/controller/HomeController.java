package edu.unialfa.java.controller;

import edu.unialfa.java.model.Usuario;
import edu.unialfa.java.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/home")
    public String homePage(@RequestParam(value = "erro", required = false) String erro, Model model) {
        if ("sem_permissao".equals(erro)) {
            model.addAttribute("mensagem", "Você não tem permissão para acessar essa página!");
            model.addAttribute("tipoMensagem", "erro");
        }

        // Pega o usuário logado pelo email via Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado: " + email));
        model.addAttribute("usuario", usuario);


        model.addAttribute("usuario", usuario);

        return "home/index";
    }
}
