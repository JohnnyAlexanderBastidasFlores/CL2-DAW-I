package pe.edu.cibertec.crud_I202313380.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Cacheable
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filmId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer releaseYear;

    private Integer rentalDuration;

    private Double rentalRate;

    private Integer length;

    private Double replacementCost;

    private String rating;

    private String specialFeatures;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmActor> filmActors = new ArrayList<>();

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmCategory> filmCategories = new ArrayList<>();

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdate = new Date();
    }

    // Getters y Setters

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Integer rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public Double getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(Double rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Double getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(Double replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<FilmActor> getFilmActors() {
        return filmActors;
    }

    public void setFilmActors(List<FilmActor> filmActors) {
        this.filmActors = filmActors;
    }

    public List<FilmCategory> getFilmCategories() {
        return filmCategories;
    }

    public void setFilmCategories(List<FilmCategory> filmCategories) {
        this.filmCategories = filmCategories;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
