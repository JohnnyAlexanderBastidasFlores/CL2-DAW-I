package pe.edu.cibertec.crud_I202313380.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.crud_I202313380.model.Language;
import pe.edu.cibertec.crud_I202313380.repository.LanguageRepository;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }
}
