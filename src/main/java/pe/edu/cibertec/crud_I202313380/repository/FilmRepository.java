package pe.edu.cibertec.crud_I202313380.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.crud_I202313380.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}
