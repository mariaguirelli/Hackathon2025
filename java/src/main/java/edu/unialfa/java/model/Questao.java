package edu.unialfa.java.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prova_id", nullable = false)
    private Prova prova;

    @Column(columnDefinition = "TEXT")
    private String enunciado;

    private String alternativaA;

    private String alternativaB;

    private String alternativaC;

    private String alternativaD;

    private String alternativaE;

    @Column(length = 1)
    private String respostaCorreta; // A, B, C, D ou E

    private Double valor;
}
