package edu.unialfa.java.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "alunos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 5)
    private String ra;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @PrePersist
    private void gerarRa() {
        if (this.ra == null) {
            this.ra = gerarRaUnico();
        }
    }

    private String gerarRaUnico() {
        Random random = new Random();
        String novoRa;

        novoRa = String.format("%05d", random.nextInt(100000));
        return novoRa;
    }
}
