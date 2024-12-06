package pe.edu.cibertec.crud_I202313380.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.cibertec.crud_I202313380.dto.FilmCreateUpdateDTO;
import pe.edu.cibertec.crud_I202313380.dto.FilmDetailDTO;
import pe.edu.cibertec.crud_I202313380.dto.FilmListDTO;
import pe.edu.cibertec.crud_I202313380.model.Category;
import pe.edu.cibertec.crud_I202313380.model.Film;
import pe.edu.cibertec.crud_I202313380.model.Language;
import pe.edu.cibertec.crud_I202313380.repository.CategoryRepository;
import pe.edu.cibertec.crud_I202313380.repository.FilmRepository;
import pe.edu.cibertec.crud_I202313380.repository.LanguageRepository;
import pe.edu.cibertec.crud_I202313380.service.FilmService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Obtiene la lista de todas las películas.
     */
    @Override
    @Cacheable("films")
    public List<FilmListDTO> getAllFilms() {
        return filmRepository.findAll().stream()
                .map(film -> new FilmListDTO(
                        film.getFilmId(),
                        film.getTitle(),
                        film.getLanguage().getName(),
                        film.getRentalDuration(),
                        film.getRentalRate()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene los detalles de una película por ID.
     */
    @Override
    @Cacheable(value = "film", key = "#id")
    public FilmDetailDTO getFilmById(Long id) {
        return filmRepository.findById(id)
                .map(film -> new FilmDetailDTO(
                        film.getFilmId(),
                        film.getTitle(),
                        film.getDescription(),
                        film.getReleaseYear(),
                        film.getRentalDuration(),
                        film.getRentalRate(),
                        film.getLength(),
                        film.getReplacementCost(),
                        film.getRating(),
                        film.getSpecialFeatures(),
                        film.getLanguage().getLanguageId(),
                        film.getLanguage().getName(),
                        film.getCategories().stream()
                                .map(Category::getCategoryId)
                                .collect(Collectors.toList()),
                        film.getCategories().stream()
                                .map(Category::getName)
                                .collect(Collectors.joining(", "))
                )).orElse(null);
    }

    /**
     * Crea una nueva película.
     */
    @Override
    @CacheEvict(value = {"films", "film"}, allEntries = true)
    public Boolean createFilm(FilmCreateUpdateDTO filmCreateUpdateDTO) {
        Film film = mapDtoToEntity(filmCreateUpdateDTO, new Film());
        filmRepository.save(film);
        return true;
    }

    /**
     * Actualiza una película existente.
     */
    @Override
    @CacheEvict(value = {"films", "film"}, allEntries = true)
    public Boolean updateFilm(Long id, FilmCreateUpdateDTO filmCreateUpdateDTO) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with ID: " + id));
        film = mapDtoToEntity(filmCreateUpdateDTO, film);
        filmRepository.save(film);
        return true;
    }

    /**
     * Elimina una película por ID.
     *
     * @return true si la eliminación fue exitosa
     */
    @Transactional
    @Override
    @CacheEvict(value = {"films", "film"}, allEntries = true)
    public Boolean deleteFilm(Long filmId) {
        // Buscar la película
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("Film not found"));

        // Limpiar las asociaciones relacionadas
        film.getFilmCategories().clear();
        film.getFilmActors().clear();
        film.getInventories().clear();

        // Guardar los cambios para actualizar las asociaciones en la base de datos
        filmRepository.save(film);

        // Sincronizar la sesión de Hibernate para evitar problemas de caché
        entityManager.unwrap(Session.class).flush();
        entityManager.unwrap(Session.class).clear();

        // Eliminar la película después de sincronizar
        filmRepository.deleteById(filmId);

        return true; // Devuelve `true` para indicar éxito
    }

    /**
     * Mapea un DTO de creación/actualización a una entidad Film.
     */
    private Film mapDtoToEntity(FilmCreateUpdateDTO dto, Film film) {
        // Mapeo de campos básicos
        film.setTitle(dto.getTitle());
        film.setDescription(dto.getDescription());
        film.setReleaseYear(dto.getReleaseYear());
        film.setRentalDuration(dto.getRentalDuration());
        film.setRentalRate(dto.getRentalRate());
        film.setLength(dto.getLength());
        film.setReplacementCost(dto.getReplacementCost());
        film.setRating(dto.getRating());
        film.setSpecialFeatures(dto.getSpecialFeatures());

        // Mapeo de idioma
        Language language = languageRepository.findById(dto.getLanguageId())
                .orElseThrow(() -> new RuntimeException("Language not found with ID: " + dto.getLanguageId()));
        film.setLanguage(language);

        // Mapeo de categorías
        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            if (categories.size() != dto.getCategoryIds().size()) {
                throw new RuntimeException("Some categories were not found");
            }
            film.setCategories(categories);
        } else {
            film.setCategories(List.of());
        }

        return film;
    }
}
