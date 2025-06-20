package edu.unialfa.java.service;

import edu.unialfa.java.exception.CpfDuplicadoException;
import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.model.Professor;
import edu.unialfa.java.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

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

    public void salvar(Professor professor) {
        if (professor.getDataNascimento() != null && professor.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento não pode ser no futuro.");
        }

        if (professor.getRegistro() == null || professor.getRegistro().isEmpty()) {
            professor.setRegistro(gerarRegistroUnico());
        }

        Professor existente = professorRepository.findByCpf(professor.getCpf());
        if (existente != null && !existente.getId().equals(professor.getId())) {
            throw new CpfDuplicadoException("CPF já cadastrado para outro professor.");
        }
        professorRepository.save(professor);
    }

    public void excluir(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new EntityNotFoundException("Professor não encontrado com o ID: " + id);
        }
        professorRepository.deleteById(id);
    }

    private String gerarRegistroUnico() {
        Random random = new Random();
        String registro;
        do {
            registro = String.format("%05d", random.nextInt(100000)); // 00000 a 99999 com zeros à esquerda
        } while (professorRepository.existsByRegistro(registro));
        return registro;
    }
}
