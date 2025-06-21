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

    public List<TurmaDisciplina> listarPorProfessorLogado() {
        // Aqui você deve obter o professor logado do contexto de segurança
        // Exemplo fictício:
        Long professorId = obterProfessorLogadoId();

        return repository.findByProfessorId(professorId);
    }

    private Long obterProfessorLogadoId() {
        // Implementar pegar o ID do professor logado pelo SecurityContext
        return 1L; // placeholder
    }
}
