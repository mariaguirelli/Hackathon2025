package edu.unialfa.java.service;

import edu.unialfa.java.model.TurmaDisciplina;
import edu.unialfa.java.repository.TurmaDisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurmaDisciplinaService {

    private final TurmaDisciplinaRepository repository;

    public List<TurmaDisciplina> listarTodos() {
        return repository.findAll();
    }

    public void salvar(TurmaDisciplina turmaDisciplina) {
        repository.save(turmaDisciplina);
    }

    public Optional<TurmaDisciplina> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
