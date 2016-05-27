package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Utilisateur;
import com.royken.bracongo.survey.service.IUtilisateurService;
import com.royken.bracongo.survey.service.ServiceException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    
    private String password;
    
    private String nom;
    
    @EJB
    private IUtilisateurService utilisateurService;
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
        public String validateUsernamePassword() {
            System.out.println("je suis ici");
        try {
            // boolean valid = LoginDAO.validate(user, pwd);
            Utilisateur utilisateur = utilisateurService.findByLoginAndPassword(username, password);
            if (utilisateur != null) {
                  HttpSession session = SessionBean.getSession();
                 session.setAttribute("username", utilisateur.getName());
                return "admin";
            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Login ou mot de passe incorrect",
                                "Veuillez entrer des valeurs valides"));
                return "login";
            }
        } catch (ServiceException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "login";
    }

        //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionBean.getSession();
        session.invalidate();
        return "login";
    }
    
}
