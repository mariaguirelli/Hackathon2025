package edu.unialfa.java.service;

import edu.unialfa.java.dto.NotaPorDisciplinaDTO;
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

    public List<NotaPorDisciplinaDTO> listarNotas(Long alunoId, Long turmaId, Integer bimestre) {
        return notaRepository.buscarNotasPorTurmaEBimestre(alunoId, turmaId, bimestre);
    }
}
