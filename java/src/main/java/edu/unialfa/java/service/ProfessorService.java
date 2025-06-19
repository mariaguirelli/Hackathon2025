package edu.unialfa.java.service;

import edu.unialfa.java.model.Professor;
import edu.unialfa.java.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    public Professor buscarPorId(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado com o ID: " + id));
    }

    public Professor salvar(Professor professor) {
        return professorRepository.save(professor);
    }

    public void excluir(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new EntityNotFoundException("Professor não encontrado com o ID: " + id);
        }
        professorRepository.deleteById(id);
    }
}
