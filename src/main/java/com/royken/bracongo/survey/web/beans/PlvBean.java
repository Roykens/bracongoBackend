package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.PLV;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.service.IPlvService;
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
@Named(value = "plvBean")
@RequestScoped
public class PlvBean {

    @EJB
    private IPlvService plvService;
    
    private PLV plv = new PLV();
    
    private List<PLV> plvs;
    
    /**
     * Creates a new instance of PlvBean
     */
    public PlvBean() {
    }

    public IPlvService getPlvService() {
        return plvService;
    }

    public void setPlvService(IPlvService plvService) {
        this.plvService = plvService;
    }

    public PLV getPlv() {
        return plv;
    }

    public void setPlv(PLV plv) {
        this.plv = plv;
    }

    public List<PLV> getPlvs() {
        try {
            plvs = plvService.findAllPlv();
            return plvs;
        } catch (ServiceException ex) {
            Logger.getLogger(PlvBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setPlvs(List<PLV> plvs) {
        this.plvs = plvs;
    }
    
    public void saveOrUpdateZone() throws ServiceException{
        if (plv != null && plv.getNom() != null) {
            plvService.saveOrUpdatePlv(plv);
            if (plv.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", plv.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", plv.getNom()+ " a été enregistré"));
            }

            plv = new PLV();
        }
        
    }
    
    public void deleteZone() throws ServiceException{
        if (plv != null && plv.getId() != null) {
            plvService.deletePlv(plv.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", plv.getNom() + " a été supprimé"));
            plv = new PLV();
        }
    }
    
     public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (plv != null && plv.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un plv avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (plv != null && plv.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un plv avant de supprimer "));
        }
    }
    
}
