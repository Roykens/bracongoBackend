package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.service.IPlanningService;
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
@Named(value = "planningBean")
@RequestScoped
public class PlanningBean {

    @EJB
    private IPlanningService planningService;
    
    private Planning planning = new Planning();
    
    private List<Planning> plannings;
    /**
     * Creates a new instance of PlanningBean
     */
    public PlanningBean() {
    }

    public IPlanningService getPlanningService() {
        return planningService;
    }

    public void setPlanningService(IPlanningService planningService) {
        this.planningService = planningService;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public List<Planning> getPlannings() {
        try {
            plannings = planningService.findAllPlanning();
            return plannings;
        } catch (ServiceException ex) {
            Logger.getLogger(PlanningBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setPlannings(List<Planning> plannings) {
        this.plannings = plannings;
    }
    
    public void saveOrUpdateZone() throws ServiceException{
        if (planning != null) {
            planningService.saveOrUpdatePlanning(planning);
            if (planning.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie",   "Le planning a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", "Le planning a été enregistré"));
            }

            planning = new Planning();
        }
        
    }
    
    public void deleteZone() throws ServiceException{
        if (planning != null && planning.getId() != null) {
            planningService.deletePlanning(planning.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie",   " Planning  supprimé"));
            planning = new Planning();
        }
    }
    
     public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (planning != null && planning.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un planning avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (planning != null && planning.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un planning avant de supprimer "));
        }
    }
    
    
}
