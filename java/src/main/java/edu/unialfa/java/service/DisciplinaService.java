package edu.unialfa.java.service;

import edu.unialfa.java.model.Disciplina;
import edu.unialfa.java.repository.DisciplinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public List<Disciplina> listarTodas() {
        return disciplinaRepository.findAll();
    }

    public Disciplina buscarPorId(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada com o ID: " + id));
    }

    public Disciplina salvar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public void excluir(Long id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new EntityNotFoundException("Disciplina não encontrada com o ID: " + id);
        }
        disciplinaRepository.deleteById(id);
    }
}
