package edu.unialfa.java.service;

import edu.unialfa.java.model.Prova;
import edu.unialfa.java.model.Questao;
import edu.unialfa.java.model.TurmaDisciplina;
import edu.unialfa.java.repository.ProvaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class ProvaService {

    private final ProvaRepository provaRepository;

    public ProvaService(ProvaRepository provaRepository) {
        this.provaRepository = provaRepository;
    }

    public List<Prova> listarProvasPorProfessor(Long professorId) {
        // Aqui você precisa implementar uma query para pegar provas que são das turmas disciplinas do professor
        // Supondo que você tem uma forma de pegar TurmaDisciplina do professor.
        // Para simplificar, vamos pegar todas as provas (ajuste conforme seu sistema)
        return provaRepository.findAll();
    }

    public Prova salvar(Prova prova) {
        TurmaDisciplina td = prova.getTurmaDisciplina();
        Integer bimestre = prova.getBimestre();

        LocalDate dataAplicacao = prova.getDataAplicacao();
        if (dataAplicacao == null) {
            throw new IllegalArgumentException("Data de aplicação deve ser informada.");
        }

        LocalDate hoje = LocalDate.now();

        // 1. Data da prova deve ser futura (não pode ser hoje nem no passado)
        if (!dataAplicacao.isAfter(hoje)) {
            throw new IllegalArgumentException("Data da prova deve ser uma data futura.");
        }

        int ano = dataAplicacao.getYear();
        LocalDate inicioAno = LocalDate.of(ano, 1, 1);
        LocalDate fimAno = LocalDate.of(ano, 12, 31);

        // 2. Verifica se já existe prova no mesmo ano, turma, disciplina e bimestre
        Optional<Prova> provaExistente = provaRepository.findByTurmaDisciplinaAndBimestreAndDataAplicacaoBetween(td, bimestre, inicioAno, fimAno);

        if (provaExistente.isPresent() && (prova.getId() == null || !provaExistente.get().getId().equals(prova.getId()))) {
            throw new IllegalStateException("Já existe uma prova para esta turma, disciplina e bimestre neste ano.");
        }

        // 3. Verifica se já existe uma prova com a mesma data exata para essa turma, disciplina e bimestre
        Optional<Prova> provaMesmoDia = provaRepository.findByTurmaDisciplinaAndBimestreAndDataAplicacao(td, bimestre, dataAplicacao);
        if (provaMesmoDia.isPresent() && (prova.getId() == null || !provaMesmoDia.get().getId().equals(prova.getId()))) {
            throw new IllegalStateException("Já existe uma prova marcada para essa turma, disciplina e bimestre na mesma data.");
        }

        if (prova.getQuestoes() != null) {
            for (Questao questao : prova.getQuestoes()) {
                questao.setProva(prova); // garante associação bidirecional para persistência correta
            }
        }

        return provaRepository.save(prova);
    }


    public Prova buscarPorId(Long id) {
        return provaRepository.buscarPorIdComQuestoes(id)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada"));
    }


    public void excluir(Long id) {
        provaRepository.deleteById(id);
    }
}
