package edu.unialfa.java.service;

import edu.unialfa.java.model.Turma;
import edu.unialfa.java.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    public List<Turma> listarTodas() {
        return turmaRepository.findAll();
    }

    public Turma buscarPorId(Long id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada com o ID: " + id));
    }

    public Turma salvar(Turma turma) {
        return turmaRepository.save(turma);
    }

    public void excluir(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new EntityNotFoundException("Turma não encontrada com o ID: " + id);
        }
        turmaRepository.deleteById(id);
    }
}
