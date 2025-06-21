package edu.unialfa.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(@RequestParam(value = "erro", required = false) String erro, Model model) {
        if ("sem_permissao".equals(erro)) {
            model.addAttribute("mensagem", "Você não tem permissão para acessar essa página!");
            model.addAttribute("tipoMensagem", "erro");
        }
        return "home/index";
    }
}


