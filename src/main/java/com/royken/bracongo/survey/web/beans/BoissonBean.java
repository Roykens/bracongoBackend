package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.service.IBoissonService;
import com.royken.bracongo.survey.service.ServiceException;
import java.util.List;
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
@Named(value = "boissonBean")
@RequestScoped
public class BoissonBean {

    @EJB
    private IBoissonService boissonService;
    
    private Boisson boisson = new Boisson();
    
    private List<Boisson> boissons;
    /**
     * Creates a new instance of BoissonBean
     */
    public BoissonBean() {
    }

    public IBoissonService getBoissonService() {
        return boissonService;
    }

    public void setBoissonService(IBoissonService boissonService) {
        this.boissonService = boissonService;
    }

    public Boisson getBoisson() {
        return boisson;
    }

    public void setBoisson(Boisson boisson) {
        this.boisson = boisson;
    }

    public List<Boisson> getBoissons() {
        return boissons;
    }

    public void setBoissons(List<Boisson> boissons) {
        this.boissons = boissons;
    }
    
    public void saveOrUpdateZone() throws ServiceException{
        if (boisson != null && boisson.getNom() != null) {
            boissonService.saveOrUpdateBoisson(boisson);
            if (boisson.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", boisson.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", boisson.getNom()+ " a été enregistré"));
            }

            boisson = new Boisson();
        }
        
    }
    
    public void deleteZone() throws ServiceException{
        if (boisson != null && boisson.getId() != null) {
            boissonService.deleteBoisson(boisson.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", boisson.getNom() + " a été supprimé"));
            boisson = new Boisson();
        }
    }
    
     public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (boisson != null && boisson.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner une boisson avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (boisson != null && boisson.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner une boisson avant de supprimer "));
        }
    }
}
