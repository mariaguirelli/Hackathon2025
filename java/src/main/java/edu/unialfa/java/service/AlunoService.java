package edu.unialfa.java.service;

import edu.unialfa.java.model.Aluno;
import edu.unialfa.java.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.unialfa.java.exception.CpfDuplicadoException;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado com o ID: " + id));
    }

    public void salvar(Aluno aluno) {
        if (aluno.getDataNascimento() != null && aluno.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento não pode ser no futuro.");
        }

        if (aluno.getRa() == null || aluno.getRa().isEmpty()) {
            aluno.setRa(gerarRaUnico());
        }

        Aluno existente = alunoRepository.findByCpf(aluno.getCpf());
        if (existente != null && !existente.getId().equals(aluno.getId())) {
            throw new CpfDuplicadoException("CPF já cadastrado para outro aluno.");
        }
        alunoRepository.save(aluno);
    }

    public void excluir(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new EntityNotFoundException("Aluno não encontrado com o ID: " + id);
        }
        alunoRepository.deleteById(id);
    }

    private String gerarRaUnico() {
        Random random = new Random();
        String ra;
        do {
            ra = String.format("%05d", random.nextInt(100000)); // 00000 a 99999 com zeros à esquerda
        } while (alunoRepository.existsByRa(ra));
        return ra;
    }
}
