package edu.unialfa.java.service;

import edu.unialfa.java.model.Turma;
import edu.unialfa.java.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurmaService {

    @Autowired

    private final TurmaRepository turmaRepository;

    public List<Turma> listarTodas() {
        return turmaRepository.findAll();
    }

    public Optional<Turma> buscarPorId(Long id) {
        return turmaRepository.findById(id);
    }

    public void salvar(Turma turma) {
        turmaRepository.save(turma);
    }

    public void excluir(Long id) {
        turmaRepository.deleteById(id);
    }
}
