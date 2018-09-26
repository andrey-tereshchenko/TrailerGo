package service;

import entity.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class UserService {

    @PersistenceContext(name = "MyUnit")
    private EntityManager entityManager;

    public UserService (){

    }

    public void addAcount(AcountEntity acount) {
        UsersEntity user = acount.getUsers();
        entityManager.persist(user);
        acount.setUserId(user.getUserId());
        acount.setRole((short) 1);
        entityManager.persist(acount);
    }

    public void rateFilm(int mark, int filmId, int userId) {
        FilmsEntity film = entityManager.find(FilmsEntity.class, filmId);
        int count = film.getCounter() + 1;
        float rating = film.getRating() + mark;
        float middle_rating = rating / count;


        film.setCounter(count);
        film.setRating(((int) (middle_rating * 100)) / 100);
        entityManager.merge(film);

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setFilmId(filmId);
        ratingEntity.setMark(mark);
        ratingEntity.setUserId(userId);
        entityManager.persist(ratingEntity);
    }

    public int findRateForFilm(int userId, int filmId) {
        Query query = entityManager.createQuery("select rating from RatingEntity rating" +
                " join FilmsEntity f on f.filmId = rating.filmId " +
                "join UsersEntity u on u.userId = rating.userId " +
                "where u.userId = :userId and f.filmId = :filmId");
        query.setParameter("userId", userId);
        query.setParameter("filmId", filmId);
        List<RatingEntity> rating = query.getResultList();
        if (rating.size() == 0) {
            return 0;
        } else {
            return rating.get(0).getMark();
        }
    }

    public void addComment(CommentEntity comment) {
        entityManager.persist(comment);
    }

    public void addFilmToFavorite(UsersEntity user, FilmsEntity film) {
        if (user.getFilms() == null) {
            user.setFilms(new HashSet<FilmsEntity>());
        }
        user.getFilms().add(film);
        entityManager.merge(user);
    }

    public void removeFromFavorite(UsersEntity user, FilmsEntity filmsEntity) {
        user.getFilms().remove(filmsEntity);
        entityManager.merge(user);
    }

    public boolean checkFavoriteFilm(UsersEntity user, FilmsEntity filmsEntity) {
        if (user.getFilms() == null) {
            return false;
        } else {
            for(FilmsEntity f:user.getFilms()){
                if(f.getFilmId() == filmsEntity.getFilmId()){
                    return true;
                }
            }
            return false;
        }
    }

    public Set<FilmsEntity> getFilmByUser(int userId) {
        UsersEntity user= entityManager.find(UsersEntity.class, userId);
        return user.getFilms();
    }

    public int getCountFavoriteFilm(int userId) {
        UsersEntity user = entityManager.find(UsersEntity.class, userId);
        return user.getFilms().size();
    }

    public void mergeFilm(FilmsEntity film) {
            entityManager.merge(film);
    }

    public void mergeUser(UsersEntity usersEntity){
        entityManager.merge(usersEntity);
    }
}
