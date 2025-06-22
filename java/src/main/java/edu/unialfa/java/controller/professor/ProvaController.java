package edu.unialfa.java.controller.professor;

import edu.unialfa.java.dto.EstatisticaProvaDTO;
import edu.unialfa.java.model.Prova;
import edu.unialfa.java.model.Questao;
import edu.unialfa.java.dto.ProvaDTO;
import edu.unialfa.java.model.TurmaDisciplina;
import edu.unialfa.java.repository.ProvaRepository;
import edu.unialfa.java.service.ProvaService;
import edu.unialfa.java.service.TurmaDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/professor/provas")
public class ProvaController {

    private final ProvaService provaService;
    private final TurmaDisciplinaService turmaDisciplinaService;
    @Autowired
    private ProvaRepository provaRepository;

    public ProvaController(ProvaService provaService, TurmaDisciplinaService turmaDisciplinaService) {
        this.provaService = provaService;
        this.turmaDisciplinaService = turmaDisciplinaService;
    }

    @GetMapping
    public String listarProvas(Model model) {
        List<Prova> provas = provaService.listarProvasPorProfessorLogado();
        model.addAttribute("provas", provas);
        return "professor/provas/index";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("prova", new Prova());
        model.addAttribute("turmaDisciplinas", turmaDisciplinaService.listarPorProfessorLogado());
        return "professor/provas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Prova prova, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("turmaDisciplinas", turmaDisciplinaService.listarPorProfessorLogado());
            model.addAttribute("mensagem", "Por favor, corrija os erros no formulário.");
            model.addAttribute("tipoMensagem", "erro");
            return "professor/provas/form";
        }

        try {
            provaService.salvar(prova);
            redirectAttributes.addFlashAttribute("mensagem", "Prova salva com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
            return "redirect:/professor/provas";
        } catch (IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("mensagem", e.getMessage());
            model.addAttribute("tipoMensagem", "erro");
            model.addAttribute("turmaDisciplinas", turmaDisciplinaService.listarPorProfessorLogado());
            return "professor/provas/form";
        }
    }

    @GetMapping("/estatisticas")
    public String exibirEstatisticas(
            @RequestParam(required = false) Long turmaDisciplinaId,
            @RequestParam(required = false) Integer bimestre,
            @RequestParam(required = false) Long provaId,
            Model model) {

        model.addAttribute("turmaDisciplinas", turmaDisciplinaService.listarPorProfessorLogado());

        if (turmaDisciplinaId != null) {
            turmaDisciplinaService.buscarPorId(turmaDisciplinaId).ifPresent(td ->
                    model.addAttribute("turmaDisciplinaSelecionada", td)
            );
        }

        if (turmaDisciplinaId != null && bimestre != null) {
            List<ProvaDTO> provas = provaService.buscarProvasPorTurmaDisciplinaEBimestre(turmaDisciplinaId, bimestre);
            model.addAttribute("provas", provas);
        }

        if (provaId != null) {
            Prova prova = provaService.buscarPorId(provaId);
            EstatisticaProvaDTO estatisticas = provaService.calcularEstatisticas(provaId);
            model.addAttribute("prova", prova);
            model.addAttribute("estatisticas", estatisticas);
        }

        // Para manter os filtros preenchidos
        model.addAttribute("bimestreSelecionado", bimestre);
        model.addAttribute("provaSelecionada", provaId);

        return "professor/provas/estatisticas";
    }


    @GetMapping("/editar/{id}")
    public String editarProva(@PathVariable Long id, Model model) {
        Prova prova = provaService.buscarPorId(id);

        System.out.println("Quantidade de questões: " + prova.getQuestoes().size());
        for (Questao q : prova.getQuestoes()) {
            System.out.println("Questão: " + q.getEnunciado());
        }

        model.addAttribute("prova", prova);
        model.addAttribute("turmaDisciplinas", turmaDisciplinaService.listarTodos());
        return "professor/provas/form";
    }


    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            provaService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Prova excluída com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir prova: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/professor/provas";
    }

    @GetMapping("/provas-por-filtro")
    public List<ProvaDTO> getProvasPorFiltro(
            @RequestParam Long turmaDisciplinaId,
            @RequestParam Integer bimestre) {

        List<Prova> provas = provaRepository.findByTurmaDisciplinaIdAndBimestre(turmaDisciplinaId, bimestre);

        // Converter para DTO para evitar problemas de lazy loading e JSON infinito
        return provas.stream().map(prova -> new ProvaDTO(prova.getId(), prova.getTitulo())).toList();
    }
}
