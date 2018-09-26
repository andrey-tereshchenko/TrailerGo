package controller;

import entity.*;
import service.AdminService;
import service.FilmService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.management.Query;
import java.lang.reflect.Array;
import java.util.*;

@ManagedBean
@SessionScoped
public class AddFilmController {
    private FilmsEntity film;

    private ActorsEntity actor;
    Set<ActorsEntity> actors;

    private DirectorsEntity director;
    Set<DirectorsEntity> directors;

    private CountryEntity country;
    Set<CountryEntity> countries;

    private GenresEntity genre;
    Set<GenresEntity> genres;

    private Date date;

    @ManagedProperty(value = "#{showFilmsController}")
    private ShowFilmsController showFilmsController;

    @EJB
    private AdminService adminService;

    @EJB
    private FilmService filmService;

    public AddFilmController() {
        film = new FilmsEntity();
        actor = new ActorsEntity();
        genre = new GenresEntity();
        country = new CountryEntity();
        director = new DirectorsEntity();
    }

    public AddFilmController(FilmsEntity film, ActorsEntity actor, Set<ActorsEntity> actors, DirectorsEntity director, Set<DirectorsEntity> directors, CountryEntity country, Set<CountryEntity> countries, GenresEntity genre, Set<GenresEntity> genres) {
        this.film = film;
        this.actor = actor;
        this.actors = actors;
        this.director = director;
        this.directors = directors;
        this.country = country;
        this.countries = countries;
        this.genre = genre;
        this.genres = genres;
    }

    public FilmsEntity getFilm() {
        if (film == null) {
            film = new FilmsEntity();
        }
        return film;
    }

    public ShowFilmsController getShowFilmsController() {
        return showFilmsController;
    }

    public void setShowFilmsController(ShowFilmsController showFilmsController) {
        this.showFilmsController = showFilmsController;
    }

    public void setFilm(FilmsEntity film) {
        this.film = film;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public String addActor() {
        if (actors == null) {
            actors = new HashSet<>();
        }
        actors.add(actor);
        actor = new ActorsEntity();
        return "addFilm";
    }

    public String addDirector() {
        if (directors == null) {
            directors = new HashSet<>();
        }
        directors.add(director);
        director = new DirectorsEntity();
        return "addFilm";
    }

    public String addGenre() {
        if (genres == null) {
            genres = new HashSet<>();
        }
        genres.add(genre);
        this.genre = new GenresEntity();
        return "addFilm";
    }

    public String addCountry() {
        if (countries == null) {
            countries = new HashSet<>();
        }
        countries.add(country);
        this.country = new CountryEntity();
        return "addFilm";
    }

    public String addFilm() {
        if (actors != null && directors != null && genres != null && countries != null) {
            film.setPremiere(new java.sql.Date(date.getTime()));
            film.setRating(0);
            film.setCounter(0);
            adminService.addFilm(film, actors, directors, genres, countries);
            film = new FilmsEntity();
            actor = new ActorsEntity();
            genre = new GenresEntity();
            country = new CountryEntity();
            director = new DirectorsEntity();
            actors = new HashSet<>();
            directors = new HashSet<>();
            genres = new HashSet<>();
            countries = new HashSet<>();
        }
        showFilmsController.setFullList(filmService.findAllFilms());
        return showFilmsController.findFilmsByPages(1);
    }

}
