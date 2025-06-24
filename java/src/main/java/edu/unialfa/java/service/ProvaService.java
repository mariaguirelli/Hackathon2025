package edu.unialfa.java.service;

import edu.unialfa.java.dto.EstatisticaProvaDTO;
import edu.unialfa.java.dto.ProvaDTO;
import edu.unialfa.java.model.*;
import edu.unialfa.java.repository.NotaRepository;
import edu.unialfa.java.repository.ProvaRepository;
import edu.unialfa.java.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private NotaRepository notaRepository;

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

        // Data da prova deve ser futura (não pode ser hoje nem no passado)
        if (!dataAplicacao.isAfter(hoje)) {
            throw new IllegalArgumentException("Data da prova deve ser uma data futura.");
        }

        Turma turma = td.getTurma();
        Disciplina disciplina = td.getDisciplina();

        if (turma == null || disciplina == null) {
            throw new IllegalStateException("Turma ou disciplina estão nulos.");
        }

        Integer anoLetivo = turma.getAnoLetivo();
        if (anoLetivo == null) {
            throw new IllegalStateException("Ano letivo da turma não está definido.");
        }

        TurmaDisciplina turmad = prova.getTurmaDisciplina();

        Optional<Prova> provaExistente = provaRepository.findByTurmaDisciplinaAndBimestreAndAnoLetivo(
                turmad, bimestre, anoLetivo
        );

        if (provaExistente.isPresent() && (prova.getId() == null || !provaExistente.get().getId().equals(prova.getId()))) {
            throw new IllegalStateException("Já existe uma prova para esta turma, disciplina, bimestre e ano letivo.");
        }

        // Verifica se já existe uma prova na mesma data, turma, disciplina e bimestre
        Optional<Prova> provaMesmoDia = provaRepository.findByTurmaDisciplinaAndBimestreAndDataAplicacao(td, bimestre, dataAplicacao);
        if (provaMesmoDia.isPresent() && (prova.getId() == null || !provaMesmoDia.get().getId().equals(prova.getId()))) {
            throw new IllegalStateException("Já existe uma prova marcada para essa turma, disciplina e bimestre na mesma data.");
        }

        if (prova.getQuestoes() == null || prova.getQuestoes().isEmpty()) {
            throw new IllegalArgumentException("A prova deve conter pelo menos uma questão.");
        }

        double somaNotas = prova.getQuestoes().stream()
                .mapToDouble(Questao::getValor)
                .sum();

        if (Math.abs(somaNotas - 6.0) > 0.0001) {
            throw new IllegalArgumentException("A soma das notas das questões deve ser exatamente 6.0. Atualmente está em: " + somaNotas);
        }

        // Ajusta a associação bidirecional para as questões
        for (Questao questao : prova.getQuestoes()) {
            questao.setProva(prova);
        }

        return provaRepository.save(prova);
    }


    public List<ProvaDTO> buscarProvasPorTurmaDisciplinaEBimestre(Long turmaDisciplinaId, Integer bimestre) {
        List<Prova> provas = provaRepository.findByTurmaDisciplinaIdAndBimestre(turmaDisciplinaId, bimestre);
        return provas.stream()
                .map(prova -> new ProvaDTO(prova.getId(), prova.getTitulo()))
                .toList();
    }

    public EstatisticaProvaDTO calcularEstatisticas(Long provaId) {
        List<Nota> notas = notaRepository.findByProvaId(provaId);

        if (notas.isEmpty()) {
            return new EstatisticaProvaDTO(0.0, 0.0, 0.0, 0L, 0L);
        }

        double soma = 0.0;
        double maior = Double.MIN_VALUE;
        double menor = Double.MAX_VALUE;

        for (Nota nota : notas) {
            double valor = nota.getNota();
            soma += valor;
            if (valor > maior) maior = valor;
            if (valor < menor) menor = valor;
        }

        double media = soma / notas.size();
        long alunosAcimaDaMedia = notas.stream().filter(n -> n.getNota() >= media).count();

        return new EstatisticaProvaDTO(
                media,
                maior,
                menor,
                (long) notas.size(),
                alunosAcimaDaMedia
        );
    }


    public Prova buscarPorId(Long id) {
        return provaRepository.buscarPorIdComQuestoes(id)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada"));
    }


    public void excluir(Long id) {
        if (!provaRepository.existsById(id)) {
            throw new EntityNotFoundException("Prova não encontrada com o ID: " + id);
        }

        boolean provaPossuiNotas = notaRepository.existsByProvaId(id);
        if (provaPossuiNotas) {
            throw new IllegalStateException("Não é possível excluir a prova. Existem notas vinculadas a ela.");
        }

        provaRepository.deleteById(id);
    }


    public List<Prova> buscarPorTurmaDisciplinaEBimestre(Long turmaDisciplinaId, Integer bimestre) {
        return provaRepository.findByTurmaDisciplinaIdAndBimestre(turmaDisciplinaId, bimestre);
    }
}
