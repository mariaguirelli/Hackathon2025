package edu.unialfa.java.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    @Column(name = "precisa_alterar_senha", nullable = false)
    @Builder.Default
    private Boolean precisaAlterarSenha = true;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", unique = true)
    private Aluno aluno;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", unique = true)
    private Professor professor;
}

