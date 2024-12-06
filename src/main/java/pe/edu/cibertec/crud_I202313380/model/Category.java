package pe.edu.cibertec.crud_I202313380.model;

import jakarta.persistence.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Cacheable("categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true) // Asegura que el nombre sea único y no nulo
    private String name;

    @ManyToMany(mappedBy = "categories") // Relación bidireccional con Film
    private List<Film> films = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public Category() {
    }

    // Constructor personalizado
    public Category(String name) {
        this.name = name;
    }

    // Getters y Setters
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
