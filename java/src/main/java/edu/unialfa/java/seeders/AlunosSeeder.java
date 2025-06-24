package edu.unialfa.java.seeders;

import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.repository.AlunoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AlunosSeeder {

    private final AlunoRepository alunoRepository;

    @PostConstruct
    public void seed() {
        if (alunoRepository.count() == 0) {
            alunoRepository.save(Aluno.builder()
                    .nome("João")
                    .sobrenome("Silva")
                    .cpf("25229796049")
                    .dataNascimento(LocalDate.of(2000, 1, 15))
                    .build());

            alunoRepository.save(Aluno.builder()
                    .nome("Maria")
                    .sobrenome("Oliveira")
                    .cpf("39105128013")
                    .dataNascimento(LocalDate.of(1999, 5, 20))
                    .build());

            alunoRepository.save(Aluno.builder()
                    .nome("Pedro")
                    .sobrenome("Santos")
                    .cpf("90939301091")
                    .dataNascimento(LocalDate.of(2001, 8, 10))
                    .build());

            alunoRepository.save(Aluno.builder()
                    .nome("Ana")
                    .sobrenome("Costa")
                    .cpf("83093399092")
                    .dataNascimento(LocalDate.of(2000, 12, 30))
                    .build());

            alunoRepository.save(Aluno.builder()
                    .nome("Lucas")
                    .sobrenome("Pereira")
                    .cpf("91231981091")
                    .dataNascimento(LocalDate.of(1998, 7, 5))
                    .build());

            // Novos alunos adicionados:
            alunoRepository.save(Aluno.builder()
                    .nome("Rafael")
                    .sobrenome("Moura")
                    .cpf("17972606005")
                    .dataNascimento(LocalDate.of(2002, 4, 10))
                    .build());

            alunoRepository.save(Aluno.builder()
                    .nome("Fernanda")
                    .sobrenome("Almeida")
                    .cpf("07089563006")
                    .dataNascimento(LocalDate.of(2001, 11, 23))
                    .build());

            alunoRepository.save(Aluno.builder()
                    .nome("Bruno")
                    .sobrenome("Gomes")
                    .cpf("87447108014")
                    .dataNascimento(LocalDate.of(2003, 2, 14))
                    .build());

            alunoRepository.save(Aluno.builder()
                    .nome("Camila")
                    .sobrenome("Barbosa")
                    .cpf("32190068096")
                    .dataNascimento(LocalDate.of(1999, 9, 5))
                    .build());

            alunoRepository.save(Aluno.builder()
                    .nome("Eduardo")
                    .sobrenome("Nunes")
                    .cpf("46117277008")
                    .dataNascimento(LocalDate.of(2000, 6, 18))
                    .build());
        }
    }
}
