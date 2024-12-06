package pe.edu.cibertec.crud_I202313380.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pe.edu.cibertec.crud_I202313380.model.Language;
import pe.edu.cibertec.crud_I202313380.service.LanguageService;

import java.util.List;

@Controller
public class LanguageController {
    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/languages")
    public String listLanguages(Model model) {
        List<Language> languages = languageService.getAllLanguages();

        model.addAttribute("languages", languages);

        return "languages/list";
    }
}
