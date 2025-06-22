package edu.unialfa.java.service;

import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.model.Disciplina;
import edu.unialfa.java.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

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
        disciplinaRepository.deleteById(id);
    }

    public List<Disciplina> listarDisciplinasPorAluno(Long alunoId) {
        return disciplinaRepository.findByAlunoId(alunoId);
    }
}
