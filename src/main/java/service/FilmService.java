package service;

import entity.*;

import javax.ejb.Stateless;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.*;

@Stateless
public class FilmService {

    @PersistenceContext(name = "MyUnit")
    private EntityManager entityManager;


    public FilmService() {
    }


    public List<ActorsEntity> findAll() {
        List<ActorsEntity> toys = entityManager.createQuery("select actorsEntity from ActorsEntity actorsEntity").getResultList();
        return toys;
    }

    public List<FilmsEntity> findAllFilms() {
        List<FilmsEntity> toys = entityManager.createQuery("select filmsEntity from FilmsEntity filmsEntity order by filmsEntity.yearReliaze").getResultList();
        return toys;
    }

    public FilmsEntity findOne(int id) {
        FilmsEntity film = entityManager.find(FilmsEntity.class, id);
        return film;
    }

    public Set<FilmsEntity> getFilmsByGenre(String genre) {
        List<GenresEntity> genres = entityManager.createQuery("select genresEntity from GenresEntity genresEntity where genresEntity.genreName = " + "\'" + genre + "\'").getResultList();
        GenresEntity genresEntity = genres.get(0);
        return genresEntity.getFilms();
    }

    public Set<FilmsEntity> getFilmByCountry(String country) {
        List<CountryEntity> countries = entityManager.createQuery("select country from CountryEntity country where country.countryName =" + "\'" + country + "\'").getResultList();
        CountryEntity countryEntity = countries.get(0);
        return countryEntity.getFilms();
    }

    public List<FilmsEntity> getFilmByYear(String year) {
        return entityManager.createQuery("select films from FilmsEntity  films where films.yearReliaze = " + year).getResultList();
    }

    public List<FilmsEntity> getFilmByName(String name) {
        return entityManager.createQuery("select film from FilmsEntity film where lower(film.filmName)  like lower(" + "\'%" + name + "%\') order by film.yearReliaze").getResultList();
    }

    public List<FilmsEntity> fullSearch(String[] genres, String[] countries, String yearBottom, String yearTop) {
        StringBuilder where = new StringBuilder(" where ");
        String where1 = " where ";
        if (!yearBottom.equals("")) {
            where.append("film.yearReliaze >=" + yearBottom);
        }
        if (!yearTop.equals("")) {
            if (!where.toString().equals(where1)) {
                where.append(" and ");
            }
            where.append("film.yearReliaze <= " + yearTop);
        }
        if (genres.length != 0) {
            if (!where.toString().equals(where1)) {
                where.append(" and ");
            }
            for (int i = 0; i < genres.length; i++) {
                where.append("g.genreName = \'" + genres[i] + "\'");
                if (i < genres.length - 1) {
                    where.append(" or ");
                }
            }
        }
        if (countries.length != 0) {
            if (!where.toString().equals(where1)) {
                where.append(" and ");
            }
            for (int i = 0; i < countries.length; i++) {
                where.append("c.countryName = \'" + countries[i] + "\'");
                if (i < countries.length - 1) {
                    where.append(" or ");
                }
            }
        }
        return entityManager.createQuery("select distinct film from FilmsEntity film " +
                "join film.genres g " +
                "join  film.country c" +
                where
                + " order by film.yearReliaze").getResultList();
    }

}
