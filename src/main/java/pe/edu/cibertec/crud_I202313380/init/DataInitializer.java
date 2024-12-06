package pe.edu.cibertec.crud_I202313380.init;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.cibertec.crud_I202313380.model.Category;
import pe.edu.cibertec.crud_I202313380.model.Language;
import pe.edu.cibertec.crud_I202313380.repository.CategoryRepository;
import pe.edu.cibertec.crud_I202313380.repository.LanguageRepository;

@Component
public class DataInitializer {
    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        initLanguages();
        initCategories();
    }

    private void initLanguages() {
        if (languageRepository.count() == 0) {
            languageRepository.save(new Language("Español"));
            languageRepository.save(new Language("Inglés"));
            languageRepository.save(new Language("Francés"));
            System.out.println("Idiomas iniciales creados.");
        }
    }

    private void initCategories() {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Acción"));
            categoryRepository.save(new Category("Comedia"));
            categoryRepository.save(new Category("Drama"));
            System.out.println("Categorías iniciales creadas.");
        }
    }
}
