package pe.edu.cibertec.crud_I202313380.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.cibertec.crud_I202313380.dto.FilmCreateUpdateDTO;
import pe.edu.cibertec.crud_I202313380.dto.FilmDetailDTO;
import pe.edu.cibertec.crud_I202313380.service.CategoryService;
import pe.edu.cibertec.crud_I202313380.service.FilmService;
import pe.edu.cibertec.crud_I202313380.service.LanguageService;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Controller
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final LanguageService languageService;
    private final CategoryService categoryService;

    @Autowired
    public FilmController(FilmService filmService, LanguageService languageService, CategoryService categoryService) {
        this.filmService = filmService;
        this.languageService = languageService;
        this.categoryService = categoryService;
    }

    /**
     * Muestra la lista de todas las películas.
     */
    @GetMapping
    public String listFilms(Model model) {
        model.addAttribute("films", filmService.getAllFilms());
        return "list"; // Asegúrate de que este es el nombre correcto del template.
    }

    /**
     * Muestra los detalles de una película específica.
     */
    @GetMapping("/{id}")
    public String filmDetails(@PathVariable Long id, Model model) {
        FilmDetailDTO film = filmService.getFilmById(id);
        if (film == null) {
            return "redirect:/films?error=notfound"; // Redirige si no encuentra la película.
        }
        model.addAttribute("film", film);
        return "details";
    }

    /**
     * Muestra el formulario para crear una nueva película.
     */
    @GetMapping("/new")
    public String newFilmForm(Model model) {
        model.addAttribute("film", new FilmCreateUpdateDTO());
        model.addAttribute("languages", languageService.getAllLanguages());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "form";
    }

    /**
     * Maneja el guardado de una nueva película.
     */
    @PostMapping
    public String saveFilm(
            @ModelAttribute @Valid FilmCreateUpdateDTO filmCreateUpdateDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            loadFormData(model);
            model.addAttribute("error", "Por favor corrija los errores en el formulario.");
            return "form";
        }

        try {
            filmService.createFilm(filmCreateUpdateDTO);
            return "redirect:/films?success=created";
        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad de datos al guardar la película", e);
            model.addAttribute("error", "Error de integridad de datos. Verifique los valores ingresados.");
        } catch (Exception e) {
            log.error("Error al guardar la película", e);
            model.addAttribute("error", "Error inesperado al guardar la película.");
        }

        loadFormData(model);
        return "form";
    }

    private void loadFormData(Model model) {
        model.addAttribute("languages", languageService.getAllLanguages());
        model.addAttribute("categories", categoryService.getAllCategories());
    }

    /**
     * Muestra el formulario para editar una película existente.
     */
    @GetMapping("/{id}/edit")
    public String editFilmForm(@PathVariable Long id, Model model) {
        FilmDetailDTO film = filmService.getFilmById(id);
        if (film == null) {
            return "redirect:/films?error=notfound";
        }

        FilmCreateUpdateDTO filmDto = new FilmCreateUpdateDTO();
        filmDto.setFilmId(film.getFilmId());
        filmDto.setTitle(film.getTitle());
        filmDto.setDescription(film.getDescription());
        filmDto.setReleaseYear(film.getReleaseYear());
        filmDto.setLanguageId(film.getLanguageId());
        filmDto.setCategoryIds(film.getCategoryIds());

        model.addAttribute("film", filmDto);
        model.addAttribute("languages", languageService.getAllLanguages());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "edit";
    }

    /**
     * Maneja la actualización de una película existente.
     */
    @PostMapping("/{id}")
    public String updateFilm(
            @PathVariable Long id,
            @ModelAttribute @Valid FilmCreateUpdateDTO filmCreateUpdateDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("languages", languageService.getAllLanguages());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edit";
        }

        try {
            filmService.updateFilm(id, filmCreateUpdateDTO);
            redirectAttributes.addFlashAttribute("message", "Película actualizada con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la película: " + e.getMessage());
        }

        return "redirect:/films";
    }

    @PostMapping("/{id}/delete")
    public String deleteFilm(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            log.info("Intentando eliminar la película con ID: " + id);
            filmService.deleteFilm(id);
            redirectAttributes.addFlashAttribute("message", "Película eliminada con éxito");
        } catch (Exception e) {
            log.error("Error al eliminar la película con ID: " + id, e);
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la película: " + e.getMessage());
        }
        return "redirect:/films";
    }
}
