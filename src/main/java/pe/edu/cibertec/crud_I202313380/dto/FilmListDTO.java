package pe.edu.cibertec.crud_I202313380.dto;

public class FilmListDTO {
    private Long filmId;
    private String title;
    private String languageName;
    private Integer rentalDuration;
    private Double rentalRate;
    private String description;

    public FilmListDTO(Long filmId, String title, String languageName, Integer rentalDuration, Double rentalRate, String description) {
        this.filmId = filmId;
        this.title = title;
        this.languageName = languageName;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.description = description;
    }

    public FilmListDTO(Long filmId, String title, String languageName, Integer rentalDuration, Double rentalRate) {
        this.filmId = filmId;
        this.title = title;
        this.languageName = languageName;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
    }

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

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
