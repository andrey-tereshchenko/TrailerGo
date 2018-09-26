package controller;

import entity.AcountEntity;
import entity.UsersEntity;
import service.FilmService;
import service.UserService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class RegistrationController {
    private AcountEntity acount;

    @EJB
    private UserService userService;

    public RegistrationController(){
        acount = new AcountEntity();
        acount.setUsers(new UsersEntity());
    }

    public AcountEntity getAcount() {
        return acount;
    }

    public void setAcount(AcountEntity acount) {
        this.acount = acount;
    }

    public String addAcount(){
        userService.addAcount(acount);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Вы успешно зарегистрировались !"));
        acount.getUsers().setName("");
        acount.getUsers().setSurname("");
        acount.setLogin("");
        return "main";
    }
}
