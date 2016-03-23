package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Telephone;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.service.ITelephoneService;
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
@Named(value = "telephoneBean")
@RequestScoped
public class TelephoneBean {
    
    @EJB
    private ITelephoneService telephoneService;
    
    private Telephone telephone = new Telephone();
    
    private List<Telephone> telephones;

    /**
     * Creates a new instance of TelephoneBean
     */
    public TelephoneBean() {
    }

    public ITelephoneService getTelephoneService() {
        return telephoneService;
    }

    public void setTelephoneService(ITelephoneService telephoneService) {
        this.telephoneService = telephoneService;
    }

    public Telephone getTelephone() {
        return telephone;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
    }

    public List<Telephone> getTelephones() {
        try {
            telephones = telephoneService.findAllTelephone();
            return telephones;
        } catch (ServiceException ex) {
            Logger.getLogger(TelephoneBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }
    
    public void saveOrUpdateZone() throws ServiceException{
        if (telephone != null && telephone.getNumeroSerie() != null) {
            telephoneService.saveOrUpdateTelephone(telephone);
            if (telephone.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", telephone.getImei() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", telephone.getImei()+ " a été enregistré"));
            }

            telephone = new Telephone();
        }
        
    }
    
    public void deleteZone() throws ServiceException{
        if (telephone != null && telephone.getId() != null) {
            telephoneService.deleteTelephone(telephone.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", telephone.getImei() + " a été supprimé"));
            telephone = new Telephone();
        }
    }
    
     public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (telephone != null && telephone.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un téléphone avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (telephone != null && telephone.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un téléphone avant de supprimer "));
        }
    }
    
    
}
