package edu.unialfa.java.security.config;

import edu.unialfa.java.service.UsuarioService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class VerificaAlteracaoSenhaFilter extends OncePerRequestFilter {

    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();

            var usuarioOpt = usuarioService.findByEmail(email);

            if (usuarioOpt.isPresent() && Boolean.TRUE.equals(usuarioOpt.get().getPrecisaAlterarSenha())) {
                String path = request.getRequestURI();

                // Permite acesso somente à tela de alterar senha
                if (!path.startsWith("/alterar-senha")) {
                    response.sendRedirect(request.getContextPath() + "/alterar-senha");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
