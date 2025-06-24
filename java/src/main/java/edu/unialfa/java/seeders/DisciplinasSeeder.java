package edu.unialfa.java.seeders;

import edu.unialfa.java.model.Disciplina;
import edu.unialfa.java.repository.DisciplinaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisciplinasSeeder {

    private final DisciplinaRepository disciplinaRepository;

    @PostConstruct
    public void seed() {
        if (disciplinaRepository.count() == 0) {
            disciplinaRepository.save(Disciplina.builder()
                    .nome("Programação Java")
                    .descricao("Fundamentos da linguagem Java e desenvolvimento de aplicações.")
                    .cargaHoraria(60)
                    .build());

            disciplinaRepository.save(Disciplina.builder()
                    .nome("Desenvolvimento Web")
                    .descricao("Criação de aplicações web utilizando HTML, CSS, JavaScript e frameworks.")
                    .cargaHoraria(80)
                    .build());

            disciplinaRepository.save(Disciplina.builder()
                    .nome("Banco de Dados")
                    .descricao("Modelagem, criação e manipulação de bancos de dados relacionais.")
                    .cargaHoraria(50)
                    .build());

            disciplinaRepository.save(Disciplina.builder()
                    .nome("Estruturas de Dados")
                    .descricao("Conceitos e implementação de estruturas de dados fundamentais.")
                    .cargaHoraria(40)
                    .build());
        }
    }
}
