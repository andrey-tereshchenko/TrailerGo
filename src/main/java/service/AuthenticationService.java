package service;

import entity.AcountEntity;
import entity.UsersEntity;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Stateless
public class AuthenticationService {

    @PersistenceContext(name = "MyUnit")
    private EntityManager entityManager;

    public boolean login(String username, String password) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)
                context.getExternalContext().getRequest();
        try {
            request.login(username, password);
            return true;
        } catch (ServletException e) {
            return false;
        }
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)
                context.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
    }

    public List<UsersEntity> getCurrentUser(String login) {
        Query query = entityManager.createQuery("select user from UsersEntity user join AcountEntity acount on user.id = acount.userId " +
                "where acount.login =:login ");
        query.setParameter("login", login);
        return query.getResultList();
    }

    public String getCurrentUsername() {
        return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public UsersEntity getUserByUserId(int userId) {
        UsersEntity usersEntity = entityManager.find(UsersEntity.class, userId);
        return  usersEntity;
    }
}
