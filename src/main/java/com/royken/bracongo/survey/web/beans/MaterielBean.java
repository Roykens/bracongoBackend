package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Materiel;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.service.IMaterielService;
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
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "materielBean")
@RequestScoped
public class MaterielBean {
    
    @EJB
    private IMaterielService materielService;
    
    private Materiel materiel = new Materiel();
    
    private List<Materiel> materiels;

    /**
     * Creates a new instance of MaterielBean
     */
    public MaterielBean() {
    }

    public IMaterielService getMaterielService() {
        return materielService;
    }

    public void setMaterielService(IMaterielService materielService) {
        this.materielService = materielService;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public List<Materiel> getMateriels() {
        try {
            materiels = materielService.findAllMateriel();
            return materiels;
        } catch (ServiceException ex) {
            Logger.getLogger(MaterielBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setMateriels(List<Materiel> materiels) {
        this.materiels = materiels;
    }
    
    public void saveOrUpdateMateriel() throws ServiceException{
        if (materiel != null && materiel.getNom() != null) {
            materielService.saveOrUpdateMateriel(materiel);
            if (materiel.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", materiel.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", materiel.getNom()+ " a été enregistré"));
            }

            materiel = new Materiel();
        }
        
    }
    
    public void deleteMateriel() throws ServiceException{
        if (materiel != null && materiel.getId() != null) {
            materielService.deleteMateriel(materiel.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", materiel.getNom() + " a été supprimé"));
            materiel = new Materiel();
        }
    }
    
     public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (materiel != null && materiel.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un materiel avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (materiel != null && materiel.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un materiel avant de supprimer "));
        }
    }
    
}
