package com.registro.escolar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "professores/home";  // renderiza o arquivo templates/home.html
    }
}
