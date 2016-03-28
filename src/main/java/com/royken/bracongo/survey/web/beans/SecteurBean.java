package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.service.ISecteurService;
import com.royken.bracongo.survey.service.ServiceException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "secteurBean")
@RequestScoped
public class SecteurBean implements Serializable{

    @EJB
    private ISecteurService secteurService;

    private Secteur secteur = new Secteur();

    private List<Secteur> secteurs;

    /**
     * Creates a new instance of SecteurBean
     */
    public SecteurBean() {
        //secteur = new Secteur();
    }
  
    public ISecteurService getSecteurService() {
        return secteurService;
    }

    public void setSecteurService(ISecteurService secteurService) {
        this.secteurService = secteurService;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public List<Secteur> getSecteurs() {
        try {
            secteurs = secteurService.findAllSecteur();
            return secteurs;
        } catch (ServiceException ex) {
            Logger.getLogger(SecteurBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public void saveOrUpdateSecteur() throws ServiceException {
        System.out.println("'Le secteur");
        System.out.println(secteur);
        if (secteur != null && secteur.getCode() != null) {
            secteurService.saveOrUpdateSecteur(secteur);
            if (secteur.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", secteur.getCode() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", secteur.getCode() + " a été enregistré"));
            }

            secteur = new Secteur();
        }

    }

    public void deleteSecteur() throws ServiceException {
        if (secteur != null && secteur.getId() != null) {
            secteurService.deleteSecteur(secteur.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", secteur.getCode() + " a été supprimé"));
            secteur = new Secteur();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (secteur != null && secteur.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un secteur avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (secteur != null && secteur.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un secteur avant de supprimer "));
        }
    }
    
    public void test(){
        System.out.println("J'ai cliqué sur : ");
        System.out.println(secteur);
    }
}
