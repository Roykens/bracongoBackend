package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.service.ISecteurService;
import com.royken.bracongo.survey.service.IZoneService;
import com.royken.bracongo.survey.service.ServiceException;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "zoneBean")
@RequestScoped
public class ZoneBean implements Serializable{

    @EJB
    private IZoneService zoneService;
    
    @EJB
    private ISecteurService secteurService;
    
    private Zone zone = new Zone();
    
    private List<Zone> zones ;
    
    private List<Secteur> secteurs;
    
    private String id;
    /**
     * Creates a new instance of ZoneBean
     */
    public ZoneBean() {
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public List<Zone> getZones() {
        try {
            zones = zoneService.findAllZone();
            return zones;
        } catch (ServiceException ex) {
            Logger.getLogger(ZoneBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public IZoneService getZoneService() {
        return zoneService;
    }

    public void setZoneService(IZoneService zoneService) {
        this.zoneService = zoneService;
    }

    public ISecteurService getSecteurService() {
        return secteurService;
    }

    public void setSecteurService(ISecteurService secteurService) {
        this.secteurService = secteurService;
    }

    public List<Secteur> getSecteurs() {
        try {
            secteurs = secteurService.findAllSecteur();
            return secteurs;
        } catch (ServiceException ex) {
            Logger.getLogger(ZoneBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
               
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void saveOrUpdateZone() throws ServiceException{
        if (zone != null && zone.getCode() != null) {
            zone.setSecteur(secteurService.findSecteurById(Long.valueOf(id)));
            zoneService.saveOrUpdateZone(zone);
            if (zone.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", zone.getCode() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", zone.getCode()+ " a été enregistré"));
            }
            zone = new Zone();
        }       
    }
    
    public void deleteZone() throws ServiceException{
        if (zone != null && zone.getId() != null) {
            zoneService.deleteZone(zone.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", zone.getCode() + " a été supprimé"));
            zone = new Zone();
        }
    }
    
     public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (zone != null && zone.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner une zone avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (zone != null && zone.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner une zone avant de supprimer "));
        }
    }
    
    public void test(){
        System.out.println("J'ai cliqué sur : ");
        System.out.println(zone);
    }
    
}
