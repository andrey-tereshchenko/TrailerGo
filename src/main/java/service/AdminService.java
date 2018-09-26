package service;

import entity.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Stateless
public class AdminService {
    @PersistenceContext(name = "MyUnit")
    private EntityManager entityManager;

    public  AdminService(){

    }


    public void createActor(ActorsEntity actor) {
        if (actor.getActorId() > 0) {
            entityManager.merge(actor);
        } else {
            entityManager.persist(actor);
        }
    }

    public void createGenre(GenresEntity genre) {
        if (genre.getGenreId() > 0) {
            entityManager.merge(genre);
        } else {
            entityManager.persist(genre);
        }
    }

    public void createCountry(CountryEntity country) {
        if (country.getCountryId() > 0) {
            entityManager.merge(country);
        } else {
            entityManager.persist(country);
        }
    }

    public void createDirector(DirectorsEntity director) {
        if (director.getDirectorId() > 0) {
            entityManager.merge(director);
        } else {
            entityManager.persist(director);
        }
    }

    public List<ActorsEntity> findActorByFIO(String name, String surname) {
        Query query = entityManager.createQuery("select actor from ActorsEntity actor where actor.actorName = :name and actor.actorSurname = :surname");
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        List<ActorsEntity> actors = query.getResultList();
        return actors;
    }

    public List<DirectorsEntity> findDirectorByFIO(String name, String surname) {
        Query query = entityManager.createQuery("select director from DirectorsEntity director where director.directorName = :name and director.directorSurname = :surname");
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        List<DirectorsEntity> directors = query.getResultList();
        return directors;
    }

    public List<GenresEntity> findGenreByName(String name) {
        Query query = entityManager.createQuery("select genre from GenresEntity genre where genre.genreName = :name");
        query.setParameter("name", name);
        List<GenresEntity> genres = query.getResultList();
        return genres;
    }

    public List<CountryEntity> findCountryByName(String name) {
        Query query = entityManager.createQuery("select country from CountryEntity country where country.countryName = :name");
        query.setParameter("name", name);
        List<CountryEntity> countries = query.getResultList();
        return countries;
    }

    public void addFilm(FilmsEntity film, Set<ActorsEntity> actors, Set<DirectorsEntity> directors, Set<GenresEntity> genres, Set<CountryEntity> countries) {
        entityManager.persist(film);

        film.setActors(new HashSet<ActorsEntity>());
        film.setDirectors(new HashSet<DirectorsEntity>());
        film.setGenres(new HashSet<GenresEntity>());
        film.setCountry(new HashSet<CountryEntity>());

        for (Iterator<ActorsEntity> it1 = actors.iterator(); it1.hasNext(); ) {
            ActorsEntity actor = it1.next();
            actor.setFilms(new HashSet<FilmsEntity>());
            if (findActorByFIO(actor.getActorName(), actor.getActorSurname()).size() != 0) {
                actor = findActorByFIO(actor.getActorName(), actor.getActorSurname()).get(0);
            }
            actor.getFilms().add(film);
            film.getActors().add(actor);
            createActor(actor);
        }
        for (Iterator<DirectorsEntity> it2 = directors.iterator(); it2.hasNext(); ) {
            DirectorsEntity director = it2.next();
            director.setFilms(new HashSet<FilmsEntity>());
            if (findDirectorByFIO(director.getDirectorName(), director.getDirectorSurname()).size() != 0) {
                director = findDirectorByFIO(director.getDirectorName(), director.getDirectorSurname()).get(0);
            }
            director.getFilms().add(film);
            film.getDirectors().add(director);
            createDirector(director);
        }
        for (Iterator<GenresEntity> it3 = genres.iterator(); it3.hasNext(); ) {
            GenresEntity genre = it3.next();
            genre.setFilms(new HashSet<FilmsEntity>());
            if (findGenreByName(genre.getGenreName()).size() != 0) {
                genre = findGenreByName(genre.getGenreName()).get(0);
            }
            genre.getFilms().add(film);
            film.getGenres().add(genre);
            createGenre(genre);
        }
        for (Iterator<CountryEntity> it4 = countries.iterator(); it4.hasNext(); ) {
            CountryEntity country = it4.next();
            country.setFilms(new HashSet<FilmsEntity>());
            if (findCountryByName(country.getCountryName()).size() != 0) {
                country = findCountryByName(country.getCountryName()).get(0);
            }
            country.getFilms().add(film);
            film.getCountry().add(country);
            createCountry(country);
        }
        entityManager.merge(film);
    }

    public void removeFilm(int id) {
        FilmsEntity film = entityManager.find(FilmsEntity.class, id);
        removeRating(id);
        entityManager.remove(film);
        for (ActorsEntity actor : film.getActors()) {
            actor.getFilms().remove(film);
        }
        for (GenresEntity genre : film.getGenres()) {
            genre.getFilms().remove(film);
        }
        for (DirectorsEntity director : film.getDirectors()) {
            director.getFilms().remove(film);
        }
        for (CountryEntity country : film.getCountry()) {
            country.getFilms().remove(film);
        }
        for (UsersEntity user : film.getUsers()){
            user.getFilms().remove(film);
        }
    }

    public void removeRating(int filmId){
        Query query = entityManager.createQuery("select rate from RatingEntity rate where rate.filmId = :filmId");
        query.setParameter("filmId",filmId);
        List<RatingEntity> rating = query.getResultList();
        for(RatingEntity r:rating){
            entityManager.remove(r);
        }
    }

    public void removeFavorite(int filmId){
        Query query = entityManager.createQuery("select rate from RatingEntity rate where rate.filmId = :filmId");
        query.setParameter("filmId",filmId);
        List<RatingEntity> rating = query.getResultList();
        for(RatingEntity r:rating){
            entityManager.remove(r);
        }
    }

    public void updateFilm(FilmsEntity film, List<ActorsEntity> deleteActors, List<DirectorsEntity> deleteDirectors, List<GenresEntity> deleteGenres, List<CountryEntity> deleteCountries) {

        for (ActorsEntity actor : deleteActors) {
            entityManager.merge(actor);
        }
        for (ActorsEntity actor : film.getActors()) {
            if (actor.getActorId() == 0) {
                actor.setFilms(new HashSet<FilmsEntity>());
                if (findActorByFIO(actor.getActorName(), actor.getActorSurname()).size() != 0) {
                    actor = findActorByFIO(actor.getActorName(), actor.getActorSurname()).get(0);
                }
                createActor(actor);
                actor.getFilms().add(film);
                entityManager.merge(actor);
            }
        }

        for (DirectorsEntity director : deleteDirectors) {
            entityManager.merge(director);
        }
        for (DirectorsEntity director : film.getDirectors()) {
            if (director.getDirectorId() == 0) {
                director.setFilms(new HashSet<FilmsEntity>());
                if (findDirectorByFIO(director.getDirectorName(), director.getDirectorSurname()).size() != 0) {
                    director = findDirectorByFIO(director.getDirectorName(), director.getDirectorSurname()).get(0);
                }
                createDirector(director);
                director.getFilms().add(film);
                entityManager.merge(director);
            }
        }

        for (GenresEntity genre : deleteGenres) {
            entityManager.merge(genre);
        }
        for (GenresEntity genre : film.getGenres()) {
            if (genre.getGenreId() == 0) {
                genre.setFilms(new HashSet<FilmsEntity>());
                if (findGenreByName(genre.getGenreName()).size() != 0) {
                    genre = findGenreByName(genre.getGenreName()).get(0);
                }
                createGenre(genre);
                genre.getFilms().add(film);
                entityManager.merge(genre);
            }
        }

        for (CountryEntity country : deleteCountries) {
            entityManager.merge(country);
        }
        for (CountryEntity country : film.getCountry()) {
            if (country.getCountryId() == 0) {
                country.setFilms(new HashSet<FilmsEntity>());
                if (findCountryByName(country.getCountryName()).size() != 0) {
                    country = findCountryByName(country.getCountryName()).get(0);
                }
                createCountry(country);
                country.getFilms().add(film);
                entityManager.merge(country);
            }
        }

        entityManager.merge(film);
    }
}
