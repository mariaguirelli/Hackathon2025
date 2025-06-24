package edu.unialfa.java.seeders;

import edu.unialfa.java.model.Turma;
import edu.unialfa.java.repository.TurmaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TurmaSeeder {

    private final TurmaRepository turmaRepository;

    @PostConstruct
    public void seed() {
        if (turmaRepository.count() == 0) {
            Turma turma1 = Turma.builder()
                    .nome("1 Ano A")
                    .anoLetivo(2025)
                    .build();

            Turma turma2 = Turma.builder()
                    .nome("1 Ano B")
                    .anoLetivo(2025)
                    .build();

            turmaRepository.save(turma1);
            turmaRepository.save(turma2);
        }
    }
}
