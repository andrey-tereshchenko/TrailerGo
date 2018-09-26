package controller;

import entity.FilmsEntity;
import entity.UsersEntity;
import service.AuthenticationService;
import service.FilmService;
import service.UserService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.HashSet;
import java.util.List;

@ManagedBean
@SessionScoped
public class AuthenticationController {
    private String username;
    private String password;
    private UsersEntity user;

    @ManagedProperty(value = "#{showFilmsController}")
    private ShowFilmsController showFilmsController;


    @EJB
    private AuthenticationService authenticationService;

    @EJB
    private FilmService filmService;

    @EJB
    private UserService userService;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public ShowFilmsController getShowFilmsController() {
        return showFilmsController;
    }

    public void setShowFilmsController(ShowFilmsController showFilmsController) {
        this.showFilmsController = showFilmsController;
    }



    public String login() {
        if(authenticationService.login(username, password)){
            user = getCurrentUser();
            showFilmsController.setFullList(filmService.findAllFilms());

            return showFilmsController.findFilmsByPages(1);
        }
        else{
            username = "";
            password = "";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Login failed."));
            return "login";
        }
    }

    public String logout() {
        authenticationService.logout();
        username = "";
        password = "";
        user = null;
        showFilmsController.setFullList(filmService.findAllFilms());

        return showFilmsController.findFilmsByPages(1);
    }

    public UsersEntity getCurrentUser() {
        List<UsersEntity> users = authenticationService.getCurrentUser(username);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public UsersEntity getUserByUserId(int userId) {
        return  authenticationService.getUserByUserId(userId);
    }

    public String addFilmToFavorite(FilmsEntity film) {
        if(user.getFilms() == null){
            user.setFilms(new HashSet<FilmsEntity>());
        }
        user.getFilms().add(film);
        if(film.getUsers() == null){
            film.setUsers(new HashSet<UsersEntity>());
        }
        film.getUsers().add(user);
        userService.mergeUser(user);
        userService.mergeFilm(film);
        return "main";
    }

    public String removeFromFavorite(FilmsEntity film) {
        user.getFilms().remove(film);
        film.getUsers().remove(user);
        userService.mergeUser(user);
        userService.mergeFilm(film);
        return "main";
    }

    public int getCountFavoriteFilm() {
        return user.getFilms().size();
    }
}
