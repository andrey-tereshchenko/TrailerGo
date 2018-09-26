package controller;

import entity.CommentEntity;
import entity.RatingEntity;
import entity.UsersEntity;
import service.AdminService;
import service.AuthenticationService;
import service.FilmService;
import entity.FilmsEntity;

import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import service.UserService;

import java.util.*;

@ManagedBean
@SessionScoped
public class ShowFilmsController {

    private FilmsEntity film;
    private String filmName;

    @EJB
    FilmService filmService;

    @EJB
    AuthenticationService authenticationService;

    @EJB
    UserService userService;

    @EJB
    AdminService adminService;

    @ManagedProperty(value = "#{searchController}")
    private SearchController searchController;


    private List<FilmsEntity> films;
    private List<FilmsEntity> fullList;
    private int rates[];
    private String comment;

    public ShowFilmsController() {
    }

    public ShowFilmsController(FilmsEntity film, List<FilmsEntity> films) {
        this.film = film;
        this.films = films;
    }

    public SearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public List<FilmsEntity> getFilms() {
        if (films == null) {
            fullList = filmService.findAllFilms();
            String page1 = findFilmsByPages(1);
        }
        return films;
    }

    public List<FilmsEntity> getFullList() {
        return fullList;
    }

    public void setFullList(List<FilmsEntity> fullList) {
        this.fullList = fullList;
    }

    public void setFilms(List<FilmsEntity> films) {
        this.films = films;
    }

    public FilmsEntity getFilm() {
        return film;
    }

    public void setFilm(FilmsEntity film) {
        this.film = film;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int[] getRates() {
        return rates;
    }

    public void setRates(int[] rates) {
        this.rates = rates;
    }

    public String getById(int id) {
        film = filmService.findOne(id);
        return "film_view";
    }

    public String findFilmsByPages(int page) {
        int startId = (page - 1) * 5;
        int finalId = page * 5;
        if (finalId > fullList.size()) {
            finalId = fullList.size();
        }
        films = new ArrayList<>();
        for (int i = startId; i < finalId; i++) {
            films.add(fullList.get(i));
        }
        searchRate();
        return "main";
    }

    public String getFilmsByGenre(String genre) {
        fullList = new ArrayList<>();
        fullList.addAll(filmService.getFilmsByGenre(genre));
        if (fullList.size() > 5) {
            return findFilmsByPages(1);
        } else {
            films = fullList;
            searchRate();
            return "main";
        }
    }

    public String getFilmsByCountry(String country) {
        fullList = new ArrayList<>();
        fullList.addAll(filmService.getFilmByCountry(country));
        if (fullList.size() > 5) {
            return findFilmsByPages(1);
        } else {
            films = fullList;
            searchRate();
            return "main";
        }
    }

    public String getFilmsByYear(String year) {
        fullList = filmService.getFilmByYear(year);
        if (fullList.size() > 5) {
            return findFilmsByPages(1);
        } else {
            films = fullList;
            searchRate();
            return "main";
        }
    }

    public String getFilmByName() {
        fullList = filmService.getFilmByName(filmName);
        filmName = "";
        if (fullList.size() > 5) {
            return findFilmsByPages(1);
        } else {
            films = fullList;
            if (films.size() == 0) {
                return "filmsNotFound";
            } else {
                searchRate();
                return "main";
            }
        }
    }

    public List<Long> getNumberPages() {
        ArrayList<Long> arrayList = new ArrayList<>();
        if (fullList == null) {
            fullList = filmService.findAllFilms();
        }
        int size = fullList.size();
        if (size <= 5) {
            arrayList.add(new Long(1));
        } else {
            int count = (size / 5);
            if (size % 5 != 0) {
                count += 1;
            }

            for (Long i = new Long(1); i <= count; i++) {
                arrayList.add(i);
            }
        }
        return arrayList;
    }

    public String getSearchPage() {
        searchController.setYearBottom("");
        searchController.setYearTop("");
        searchController.setGenres_2(new String[8]);
        searchController.setGenres_1(new String[8]);
        searchController.setCountries(new String[6]);

        return "search_full";
    }

    public String getRegistration() {
        return "registration";
    }

    public String getFormPage() {
        return "login";
    }

    public String fullSearch() {
        fullList = filmService.fullSearch(searchController.getGenres(), searchController.getCountries(), searchController.getYearBottom(), searchController.getYearTop());
        if (fullList.size() > 5) {
            return findFilmsByPages(1);
        } else {
            films = fullList;
            if (films.size() != 0) {
                searchRate();
                return "main";
            } else {
                return "filmsNotFound";
            }
        }
    }

    public String getMainPage() {
        fullList = filmService.findAllFilms();
        return findFilmsByPages(1);
    }

    public String removeFilm(int id) {
        adminService.removeFilm(id);
        return getMainPage();
    }

    public String rateFilm(int filmId) {
        List<UsersEntity> users = authenticationService.getCurrentUser(authenticationService.getCurrentUsername());
        if (users.size() != 0) {
            int userId = users.get(0).getUserId();
            int i = 0;
            int j = 0;
            for (FilmsEntity f : films) {
                if (f.getFilmId() == filmId) {
                    int count = f.getCounter() + 1;
                    float rating1 = f.getRating() + rates[i];
                    float middle_rating = rating1 / count;
                    f.setCounter(count);
                    f.setRating(middle_rating);
                    j = i;
                }
                i++;
            }
            userService.rateFilm(rates[j], filmId, userId);
        }
        return "main";
    }

    public int findRateForFilm(int filmId) {
        List<UsersEntity> users = authenticationService.getCurrentUser(authenticationService.getCurrentUsername());
        if (users.size() == 0) {
            return 0;
        } else {
            int userId = users.get(0).getUserId();
            return userService.findRateForFilm(userId, filmId);
        }
    }

    public void searchRate() {
        rates = new int[5];
        int i = 0;
        for (FilmsEntity f : films) {
            rates[i] = findRateForFilm(f.getFilmId());
            i++;
        }
    }

    public void addComment(int filmId) {
        List<UsersEntity> users = authenticationService.getCurrentUser(authenticationService.getCurrentUsername());
        if (users.size() != 0) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setUserId(users.get(0).getUserId());
            commentEntity.setValue(comment);
            commentEntity.setFilmId(filmId);
            commentEntity.setDate(new java.sql.Date(new Date().getTime()));
            if(film.getComments() == null){
                film.setComments(new HashSet<CommentEntity>());
            }
            film.getComments().add(commentEntity);
            userService.addComment(commentEntity);
            comment = "";
        }
    }


    public String getFilmsByFavorite() {
        List<UsersEntity> users = authenticationService.getCurrentUser(authenticationService.getCurrentUsername());
        if (users.size() != 0) {
            fullList = new ArrayList<>();
            fullList.addAll(userService.getFilmByUser(users.get(0).getUserId()));
            if (fullList.size() > 5) {
                return findFilmsByPages(1);
            } else {
                films = fullList;
                searchRate();
                return "main";
            }
        }
        return "main";
    }

    public List<CommentEntity> getSortedComment(){
        List<CommentEntity> comments = new ArrayList<>();
        comments.addAll(film.getComments());
        Collections.sort(comments);
        return comments;
    }

}
