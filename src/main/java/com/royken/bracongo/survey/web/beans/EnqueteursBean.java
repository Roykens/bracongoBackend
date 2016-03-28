package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.service.IEnqueteurService;
import com.royken.bracongo.survey.service.ServiceException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "enqueteursBean")
@RequestScoped
public class EnqueteursBean implements Serializable{

    private Enqueteur enqueteur = new Enqueteur();
    
    private List<Enqueteur> enqueteurs; 
    
    @EJB
    private IEnqueteurService enqueteurService;
    /**
     * Creates a new instance of Enqueteurs
     */
    public EnqueteursBean() {
    }

    public Enqueteur getEnqueteur() {
        return enqueteur;
    }

    public void setEnqueteur(Enqueteur enqueteur) {
        this.enqueteur = enqueteur;
    }

    public List<Enqueteur> getEnqueteurs() {
        try {
            enqueteurs =  enqueteurService.findAllEnqueteur();
            return enqueteurs;
        } catch (ServiceException ex) {
            Logger.getLogger(EnqueteursBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setEnqueteurs(List<Enqueteur> enqueteurs) {
        this.enqueteurs = enqueteurs;
    }

    public IEnqueteurService getEnqueteurService() {
        return enqueteurService;
    }

    public void setEnqueteurService(IEnqueteurService enqueteurService) {
        this.enqueteurService = enqueteurService;
    }
    
    public void saveOrUpdateEnqueteur() throws ServiceException{
      System.out.println(enqueteur);
        if (enqueteur != null && enqueteur.getMatricule() != null) {
            enqueteurService.saveOrUpdateEnqueteur(enqueteur);
            if (enqueteur.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", enqueteur.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", enqueteur.getNom() + " a été enregistré"));
            }

            enqueteur = new Enqueteur();
        }
    }
    
    public void deleteSecteur() throws ServiceException {
        if (enqueteur != null && enqueteur.getId() != null) {
            enqueteurService.deleteEnqueteur(enqueteur.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", enqueteur.getNom() + " a été supprimé"));
            enqueteur = new Enqueteur();
        }
    }

    public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (enqueteur != null && enqueteur.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un enqueteur avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (enqueteur != null && enqueteur.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un enqueteur avant de supprimer "));
        }
    }
    
    public void test(){
        System.out.println("J'ai cliqué sur : ");
        System.out.println(enqueteur);
    }
    
}
