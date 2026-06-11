package com.portfolio.portfolio.controller;

import com.portfolio.portfolio.model.Message;
import com.portfolio.portfolio.repository.MessageRepository;
import com.portfolio.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    // Инжектираме репозиторито автоматично
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MessageRepository messageRepository; // Инжектираме новото репозитори

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Иван Иванов");
        model.addAttribute("title", "Java Junior Developer Portfolio");

        // Вземаме проектите от PostgreSQL и ги подаваме на HTML-а
        model.addAttribute("projects", projectRepository.findAll());

        // Подаваме нов празен обект Message за контактната форма най-отдолу
        model.addAttribute("message", new Message());

        return "index";
    }

    @PostMapping("/contact")
    public String sendMessage(@ModelAttribute("message") Message message) {
        // Записваме съобщението от HR-а в базата данни
        messageRepository.save(message);

        // Пренасочваме обратно към началната страница
        // Може да добавим и параметър "?success", за да покажем съобщение в браузъра
        return "redirect:/?success";
    }
}