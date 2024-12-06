package pe.edu.cibertec.crud_I202313380.service;

import org.springframework.cache.annotation.Cacheable;
import pe.edu.cibertec.crud_I202313380.dto.FilmCreateUpdateDTO;
import pe.edu.cibertec.crud_I202313380.dto.FilmDetailDTO;
import pe.edu.cibertec.crud_I202313380.dto.FilmListDTO;

import java.util.List;

public interface FilmService {

    @Cacheable(value = "films")
    List<FilmListDTO> getAllFilms();

    @Cacheable(value = "film", key = "#id")
    FilmDetailDTO getFilmById(Long id);

    Boolean createFilm(FilmCreateUpdateDTO filmCreateUpdateDTO);

    Boolean updateFilm(Long id, FilmCreateUpdateDTO filmCreateUpdateDTO);

    Boolean deleteFilm(Long id);
}
