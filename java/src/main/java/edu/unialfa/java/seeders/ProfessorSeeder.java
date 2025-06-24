package edu.unialfa.java.seeders;

import edu.unialfa.java.model.Professor;
import edu.unialfa.java.repository.ProfessorRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ProfessorSeeder {

    private final ProfessorRepository professorRepository;

    @PostConstruct
    public void seed() {
        if (professorRepository.count() == 0) {
            Professor prof1 = Professor.builder()
                    .nome("Marcos")
                    .sobrenome("Vendramini")
                    .registro("04674")
                    .cpf("74638395082")
                    .dataNascimento(LocalDate.of(1980, 5, 10))
                    .build();

            Professor prof2 = Professor.builder()
                    .nome("Diogo")
                    .sobrenome("Ranghetti")
                    .registro("03648")
                    .cpf("70321432029")
                    .dataNascimento(LocalDate.of(1975, 8, 25))
                    .build();

            professorRepository.save(prof1);
            professorRepository.save(prof2);
        }
    }
}
