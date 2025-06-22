package edu.unialfa.java.service;

import edu.unialfa.java.model.*;
import edu.unialfa.java.repository.ProvaRepository;
import edu.unialfa.java.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProvaService {

    private final UsuarioRepository usuarioRepository;
    private final ProvaRepository provaRepository;

    public ProvaService(ProvaRepository provaRepository, UsuarioRepository usuarioRepository) {
        this.provaRepository = provaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Prova> listarProvasPorProfessorLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));


        if (usuario == null || usuario.getProfessor() == null) {
            throw new IllegalStateException("Usuário ou professor não encontrado ou não autenticado.");
        }

        Professor professor = usuario.getProfessor();

        return provaRepository.findByTurmaDisciplina_Professor(professor);
    }

    public Prova salvar(Prova prova) {

        List<Questao> questoesValidas = prova.getQuestoes().stream()
                .filter(q -> q != null
                        && q.getValor() != null
                        && q.getValor() > 0
                        && q.getEnunciado() != null
                        && !q.getEnunciado().trim().isEmpty())
                .collect(Collectors.toList());

        prova.setQuestoes(questoesValidas);



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

        if (prova.getQuestoes() == null || prova.getQuestoes().isEmpty()) {
            throw new IllegalArgumentException("A prova deve conter pelo menos uma questão.");
        }

        if (prova.getQuestoes() != null && !prova.getQuestoes().isEmpty()) {
            double somaNotas = prova.getQuestoes().stream()
                    .mapToDouble(Questao::getValor)
                    .sum();

            if (Math.abs(somaNotas - 6.0) > 0.0001) {
                throw new IllegalArgumentException("A soma das notas das questões deve ser exatamente 6.0. Atualmente está em: " + somaNotas);
            }
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
