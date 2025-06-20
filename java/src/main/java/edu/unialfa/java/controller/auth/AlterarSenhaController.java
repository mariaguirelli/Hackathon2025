package edu.unialfa.java.controller.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.core.Authentication;

// Caso use sua service para buscar e salvar usuário
import edu.unialfa.java.service.UsuarioService;
import edu.unialfa.java.model.Usuario;


@Controller
public class AlterarSenhaController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;

    public AlterarSenhaController(UsuarioService usuarioService, PasswordEncoder encoder) {
        this.usuarioService = usuarioService;
        this.encoder = encoder;
    }

    @GetMapping("/alterar-senha")
    public String mostrarFormAlterarSenha() {
        return "alterar_senha"; // nome do template Thymeleaf que você criou
    }

    @PostMapping("/alterar-senha")
    public String processarAlteracaoSenha(@RequestParam String novaSenha,
                                          @RequestParam String confirmarSenha,
                                          Authentication authentication,
                                          RedirectAttributes redirectAttributes) {

        // Validação: senhas não coincidem
        if (!novaSenha.equals(confirmarSenha)) {
            redirectAttributes.addFlashAttribute("mensagem", "As senhas não coincidem.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
            return "redirect:/alterar-senha";  // volta para o formulário para corrigir
        }

        String email = authentication.getName();
        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> {
                    redirectAttributes.addFlashAttribute("mensagem", "Usuário não encontrado.");
                    redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
                    // Não pode lançar exceção aqui, pois não exibirá mensagem — redireciona para /alterar-senha
                    throw new IllegalArgumentException("Usuário não encontrado");
                });

        if (encoder.matches(novaSenha, usuario.getSenha())) {
            redirectAttributes.addFlashAttribute("mensagem", "A nova senha deve ser diferente da atual.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
            return "redirect:/alterar-senha";
        }

        try {
            // Atualiza senha
            usuario.setSenha(encoder.encode(novaSenha));
            usuario.setPrecisaAlterarSenha(false);
            usuarioService.salvar(usuario);

            redirectAttributes.addFlashAttribute("mensagem", "Senha alterada com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
            return "redirect:/home"; // redireciona para home com mensagem de sucesso
        } catch (Exception e) {
            // Qualquer erro inesperado ao salvar
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao alterar senha: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
            return "redirect:/alterar-senha";  // volta para o formulário para tentar novamente
        }
    }
}
