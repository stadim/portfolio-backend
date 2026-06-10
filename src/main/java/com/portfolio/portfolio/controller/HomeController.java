package com.portfolio.portfolio.controller;

import com.portfolio.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Инжектираме репозиторито автоматично
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Иван Иванов");
        model.addAttribute("title", "Java Junior Developer Portfolio");

        // Вземаме проектите от PostgreSQL и ги подаваме на HTML-а
        model.addAttribute("projects", projectRepository.findAll());

        return "index";
    }
}