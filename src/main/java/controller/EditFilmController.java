package controller;

import entity.*;
import service.AdminService;
import service.FilmService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean
@SessionScoped
public class EditFilmController {
    private FilmsEntity film;

    private ActorsEntity actor;
    Set<ActorsEntity> actors;
    private List<ActorsEntity> deleteActors;

    private DirectorsEntity director;
    Set<DirectorsEntity> directors;
    private List<DirectorsEntity> deleteDirectors;

    private CountryEntity country;
    Set<CountryEntity> countries;
    private List<CountryEntity> deleteCountries;

    private GenresEntity genre;
    Set<GenresEntity> genres;
    private List<GenresEntity> deleteGenres;

    private Date date;

    @ManagedProperty(value = "#{showFilmsController}")
    private ShowFilmsController showFilmsController;

    @EJB
    AdminService adminService;

    @EJB
    FilmService filmService;

    public EditFilmController() {
        film = new FilmsEntity();
        actor = new ActorsEntity();
        genre = new GenresEntity();
        country = new CountryEntity();
        director = new DirectorsEntity();
        deleteActors = new ArrayList<>();
        deleteCountries = new ArrayList<>();
        deleteDirectors = new ArrayList<>();
        deleteGenres = new ArrayList<>();
    }

    public FilmsEntity getFilm() {
        return film;
    }

    public void setFilm(FilmsEntity film) {
        this.film = film;
    }

    public ActorsEntity getActor() {
        return actor;
    }

    public void setActor(ActorsEntity actor) {
        this.actor = actor;
    }

    public Set<ActorsEntity> getActors() {
        return actors;
    }

    public void setActors(Set<ActorsEntity> actors) {
        this.actors = actors;
    }

    public List<ActorsEntity> getDeleteActors() {
        return deleteActors;
    }

    public void setDeleteActors(List<ActorsEntity> deleteActors) {
        this.deleteActors = deleteActors;
    }

    public DirectorsEntity getDirector() {
        return director;
    }

    public void setDirector(DirectorsEntity director) {
        this.director = director;
    }

    public Set<DirectorsEntity> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<DirectorsEntity> directors) {
        this.directors = directors;
    }

    public List<DirectorsEntity> getDeleteDirectors() {
        return deleteDirectors;
    }

    public void setDeleteDirectors(List<DirectorsEntity> deleteDirectors) {
        this.deleteDirectors = deleteDirectors;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public Set<CountryEntity> getCountries() {
        return countries;
    }

    public void setCountries(Set<CountryEntity> countries) {
        this.countries = countries;
    }

    public List<CountryEntity> getDeleteCountries() {
        return deleteCountries;
    }

    public void setDeleteCountries(List<CountryEntity> deleteCountries) {
        this.deleteCountries = deleteCountries;
    }

    public GenresEntity getGenre() {
        return genre;
    }

    public void setGenre(GenresEntity genre) {
        this.genre = genre;
    }

    public Set<GenresEntity> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenresEntity> genres) {
        this.genres = genres;
    }

    public List<GenresEntity> getDeleteGenres() {
        return deleteGenres;
    }

    public void setDeleteGenres(List<GenresEntity> deleteGenres) {
        this.deleteGenres = deleteGenres;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public FilmService getFilmService() {
        return filmService;
    }

    public void setFilmService(FilmService filmService) {
        this.filmService = filmService;
    }

    public ShowFilmsController getShowFilmsController() {
        return showFilmsController;
    }

    public void setShowFilmsController(ShowFilmsController showFilmsController) {
        this.showFilmsController = showFilmsController;
    }

    public String addActor() {
        if (actors == null) {
            actors = new HashSet<>();
        }
        actors.add(actor);
        actor = new ActorsEntity();
        return "editFilm";
    }

    public String addDirector() {
        if (directors == null) {
            directors = new HashSet<>();
        }
        directors.add(director);
        director = new DirectorsEntity();
        return "editFilm";
    }

    public String addGenre() {
        if (genres == null) {
            genres = new HashSet<>();
        }
        genres.add(genre);
        this.genre = new GenresEntity();
        return "editFilm";
    }

    public String addCountry() {
        if (countries == null) {
            countries = new HashSet<>();
        }
        countries.add(country);
        this.country = new CountryEntity();
        return "editFilm";
    }

    public String edit(int id) {
        film = filmService.findOne(id);
        date = new Date(film.getPremiere().getTime());
        actors = film.getActors();
        directors = film.getDirectors();
        genres = film.getGenres();
        countries = film.getCountry();
        return "editFilm";
    }

    public void removeActor(int id) {
        ActorsEntity actorsEntity = new ActorsEntity();
        for (ActorsEntity a : actors) {
            if (a.getActorId() == id) {
                actorsEntity = a;
            }
        }
        if (actorsEntity.getActorId() != 0) {
            actorsEntity.getFilms().remove(film);
            deleteActors.add(actorsEntity);
        }
        actors.remove(actorsEntity);
    }

    public void removeDirector(int id) {
        DirectorsEntity directorsEntity = new DirectorsEntity();
        for (DirectorsEntity d : directors) {
            if (d.getDirectorId() == id) {
                directorsEntity = d;
            }
        }
        if (directorsEntity.getDirectorId() != 0) {
            directorsEntity.getFilms().remove(film);
            deleteDirectors.add(directorsEntity);
        }
        directors.remove(directorsEntity);
    }

    public void removeGenre(int id) {
        GenresEntity genresEntity = new GenresEntity();
        for (GenresEntity g : genres) {
            if (g.getGenreId() == id) {
                genresEntity = g;
            }
        }
        if (genresEntity.getGenreId() != 0) {
            genresEntity.getFilms().remove(film);
            deleteGenres.add(genresEntity);
        }
        genres.remove(genresEntity);
    }

    public void removeCountry(int id) {
        CountryEntity countryEntity = new CountryEntity();
        for (CountryEntity c : countries) {
            if (c.getCountryId() == id) {
                countryEntity = c;
            }
        }
        if (countryEntity.getCountryId() != 0) {
            countryEntity.getFilms().remove(film);
            deleteCountries.add(countryEntity);
        }
        countries.remove(countryEntity);
    }

    public String updateFilm() {
        film.setPremiere(new java.sql.Date(date.getTime()));
        adminService.updateFilm(film, deleteActors, deleteDirectors, deleteGenres, deleteCountries);
        showFilmsController.setFullList(filmService.findAllFilms());

        return showFilmsController.findFilmsByPages(1);
    }
}
