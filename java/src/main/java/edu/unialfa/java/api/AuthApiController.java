package edu.unialfa.java.api;

import edu.unialfa.java.exception.NotProfessorException;
import edu.unialfa.java.dto.LoginRequest;
import edu.unialfa.java.dto.LoginResponse;
import edu.unialfa.java.security.config.UsuarioUserDetails;
import edu.unialfa.java.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import edu.unialfa.java.exception.PrecisaAlterarSenhaException;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Cast para acessar métodos personalizados
        if (userDetails instanceof UsuarioUserDetails usuarioUserDetails) {
            if (usuarioUserDetails.getUsuario().getPrecisaAlterarSenha()) {
                throw new PrecisaAlterarSenhaException(); // ← exceção customizada
            }

            boolean isProfessor = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("PROFESSOR"));

            if (!isProfessor) {
                throw new NotProfessorException();
            }

            String token = jwtService.generateToken(userDetails);
            return new LoginResponse(token, usuarioUserDetails.getNome());
        }

        throw new RuntimeException("Erro ao processar login");
    }
}
