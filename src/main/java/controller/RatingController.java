package controller;

import service.FilmService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class RatingController {

    @EJB
    private FilmService filmService;

    @ManagedProperty(value = "#{showFilmsController}")
    private ShowFilmsController showFilmsController;

    @ManagedProperty(value = "#{authenticationController}")
    private AuthenticationController authenticationController;
    public RatingController(){

    }

    public ShowFilmsController getShowFilmsController() {
        return showFilmsController;
    }

    public void setShowFilmsController(ShowFilmsController showFilmsController) {
        this.showFilmsController = showFilmsController;
    }

    public AuthenticationController getAuthenticationController() {
        return authenticationController;
    }

    public void setAuthenticationController(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }
}
