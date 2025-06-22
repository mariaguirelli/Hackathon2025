package edu.unialfa.java.service;

import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.model.Nota;
import edu.unialfa.java.repository.NotaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaService {

    @Autowired

    private NotaRepository notaRepository;

    public List<Nota> listarTodos() {
        return notaRepository.findAll();
    }

    public Nota buscarPorId(Long id) {
        return notaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota não encontrada com o ID: " + id));
    }

    public List<Nota> listarNotasPorAluno(Long alunoId) {
        return notaRepository.findByAlunoId(alunoId);
    }

}
