package edu.unialfa.java.security.config;

import edu.unialfa.java.model.Usuario;
import edu.unialfa.java.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Optional;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioService usuarioService;

    public CustomAuthenticationSuccessHandler(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Pega o email do usuário logado
        String email = authentication.getName();

        // Busca o usuário no banco (retorna Optional)
        Optional<Usuario> usuarioOpt = usuarioService.findByEmail(email);

        if (usuarioOpt.isPresent() && Boolean.TRUE.equals(usuarioOpt.get().getPrecisaAlterarSenha())) {
            // Redireciona para página de alteração de senha
            response.sendRedirect(request.getContextPath() + "/alterar-senha");
        } else {
            // Redireciona para a página padrão (exemplo: dashboard)
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

}
