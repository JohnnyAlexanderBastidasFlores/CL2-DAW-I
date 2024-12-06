package pe.edu.cibertec.crud_I202313380.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "film_category")
public class FilmCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "film_id", nullable = false, foreignKey = @ForeignKey(name = "fk_film_category_film"))
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_film_category_category"))
    private Category category;

    @Column(nullable = false, updatable = false)
    private LocalDateTime lastUpdate;

    // Se actualiza automáticamente antes de cualquier persistencia o actualización
    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // Sobrescritura de equals y hashCode para garantizar consistencia en colecciones y comparaciones
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmCategory that = (FilmCategory) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
