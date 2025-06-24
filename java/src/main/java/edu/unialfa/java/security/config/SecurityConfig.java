package edu.unialfa.java.security.config;

import edu.unialfa.java.security.filter.JwtAuthenticationFilter;
import edu.unialfa.java.service.JwtService;
import edu.unialfa.java.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;

    @Lazy
    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(usuarioService);
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http,
                                              JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
                .securityMatcher("/api/**")  // só para rotas API
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()  // LIBERA OPTIONS
                        .requestMatchers("/api/login").permitAll()
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity http,
                                              AuthenticationSuccessHandler successHandler,
                                              VerificaAlteracaoSenhaFilter senhaFilter,
                                              CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
        return http
                .securityMatcher("/**") // todas as outras rotas (web)
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(senhaFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/alterar-senha", "/alterar-senha/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/professor/**").hasAuthority("PROFESSOR")
                        .requestMatchers("/aluno/**").hasAuthority("ALUNO")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler))
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(usuarioDetailsService)
                .build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, UsuarioDetailsService usuarioDetailsService) {
        return new JwtAuthenticationFilter(jwtService, usuarioDetailsService);
    }


}
