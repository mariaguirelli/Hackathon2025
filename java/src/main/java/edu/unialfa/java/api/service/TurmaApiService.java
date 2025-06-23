package edu.unialfa.java.api.service;

import edu.unialfa.java.api.dto.TurmaDTO;
import edu.unialfa.java.model.Turma;
import edu.unialfa.java.model.Usuario;
import edu.unialfa.java.repository.TurmaDisciplinaRepository;
import edu.unialfa.java.repository.TurmaRepository;
import edu.unialfa.java.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaApiService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    public List<TurmaDTO> buscarTurmasDoProfessorPorEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty() || usuarioOpt.get().getProfessor() == null) {
            throw new RuntimeException("Professor não encontrado com esse e-mail.");
        }

        Long professorId = usuarioOpt.get().getProfessor().getId();

        List<Long> turmaIds = turmaDisciplinaRepository.findTurmaIdsByProfessorId(professorId);

        List<Turma> turmas = turmaRepository.findAllById(turmaIds);

        return turmas.stream()
                .map(t -> new TurmaDTO(t.getId(), t.getNome(), t.getAnoLetivo()))
                .collect(Collectors.toList());
    }
}
