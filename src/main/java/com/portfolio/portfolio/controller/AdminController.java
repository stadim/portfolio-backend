package com.portfolio.portfolio.controller;


import com.portfolio.portfolio.model.Project;
import com.portfolio.portfolio.repository.MessageRepository;
import com.portfolio.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin") // Всички адреси в този контролер ще започват с /admin
public class AdminController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MessageRepository messageRepository; // 1. Инжектираме репозиторито за съобщения

    // 1. Показване на списъка с проекти (Dashboard)
    // Достъпно на: http://localhost:8080/admin
    @GetMapping
    public String dashboard(Model model) {
        // Подаваме проектите
        model.addAttribute("projects", projectRepository.findAll());

        // 2. Вземаме всички съобщения от PostgreSQL и ги подаваме на HTML-а
        model.addAttribute("messages", messageRepository.findAll());

        return "admin/dashboard";
    }

    // 2. Показване на формата за добавяне на нов проект
    // Достъпно на: http://localhost:8080/admin/projects/new
    @GetMapping("/projects/new")
    public String showCreateForm(Model model) {
        // Подаваме празен обект Project, който формата ще "напълни" с данни
        model.addAttribute("project", new Project());
        return "admin/project-form"; // Търси файл project-form.html в папка admin
    }

    // ... предишния код на контролера ...

    // 3. Обработка на изпратената форма и запис в базата данни
    @PostMapping("/projects")
    public String saveProject(@ModelAttribute("project") Project project) {
        // Записваме обекта в PostgreSQL чрез репозиторито
        projectRepository.save(project);

        // Пренасочваме потребителя обратно към списъка (Dashboard-а)
        return "redirect:/admin";
    }

    // 4. Изтриване на проект по неговото ID
    // Достъпно на: /admin/projects/delete/{id}
    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable("id") Long id) {
        // Изтриваме от PostgreSQL по първичен ключ
        projectRepository.deleteById(id);

        // Връщаме се обратно в админ панела
        return "redirect:/admin";
    }

    @GetMapping("/projects/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        // Намираме проекта по ID или хвърляме грешка, ако не съществува
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Невалидно ID: " + id));

        // Подаваме намерения проект на формата
        model.addAttribute("project", project);
        return "admin/project-form";
    }
}