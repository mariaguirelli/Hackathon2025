package edu.unialfa.java.service;

import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.model.Disciplina;
import edu.unialfa.java.repository.DisciplinaRepository;
import edu.unialfa.java.repository.TurmaDisciplinaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    public List<Disciplina> listarTodas() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> buscarPorId(Long id) {
        return disciplinaRepository.findById(id);
    }

    public void salvar(Disciplina disciplina) {
        disciplinaRepository.save(disciplina);
    }

    public void excluir(Long id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new EntityNotFoundException("Disciplina não encontrada com o ID: " + id);
        }

        boolean disciplinaVinculadaEmTurmaDisciplina = turmaDisciplinaRepository.existsByDisciplinaId(id);

        if (disciplinaVinculadaEmTurmaDisciplina) {
            throw new IllegalStateException("Não é possível excluir a disciplina. Ela está vinculada a uma ou mais turmas.");
        }

        disciplinaRepository.deleteById(id);
    }
  
    public List<Disciplina> listarDisciplinasPorAluno(Long alunoId) {
        return disciplinaRepository.findByAlunoId(alunoId);
    }

}
