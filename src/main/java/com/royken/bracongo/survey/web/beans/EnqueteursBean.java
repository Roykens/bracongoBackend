package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.service.IEnqueteurService;
import com.royken.bracongo.survey.service.ServiceException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "enqueteursBean")
@RequestScoped
public class EnqueteursBean {

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
            return enqueteurService.findAllEnqueteur();
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
    
    public void saveEnqueteur(){
        try {
            enqueteurService.saveOrUpdateEnqueteur(enqueteur);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération reussie", enqueteur.getNom() + " a été enregistré "));
        } catch (ServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Echec opération", enqueteur.getNom() + " n'a pas été enregistré "));
            Logger.getLogger(EnqueteursBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
