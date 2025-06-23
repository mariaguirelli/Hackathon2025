package edu.unialfa.java.api;

import edu.unialfa.java.exception.NotProfessorException;
import edu.unialfa.java.dto.LoginRequest;
import edu.unialfa.java.dto.LoginResponse;
import edu.unialfa.java.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        // 1) Autentica: se credenciais inválidas, lança BadCredentialsException
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 2) Verifica perfil PROFESSOR: se não, lança NotProfessorException
        boolean isProfessor = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("PROFESSOR"));

        if (!isProfessor) {
            throw new NotProfessorException();
        }

        // 3) Gera e retorna token
        String token = jwtService.generateToken(userDetails);
        return new LoginResponse(token);
    }
}
