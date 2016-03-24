package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.service.ICircuitService;
import com.royken.bracongo.survey.service.IZoneService;
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
@Named(value = "circuitBean")
@RequestScoped
public class CircuitBean implements Serializable{
    
    @EJB
    private ICircuitService circuitService;
    
    @EJB
    private IZoneService zoneService;
    
    private Circuit circuit = new Circuit();
    
    private List<Circuit> circuits;
    
    private List<Zone> zones;

    String id;
    
    /**
     * Creates a new instance of CircuitBean
     */
    public CircuitBean() {
    }

    public ICircuitService getCircuitService() {
        return circuitService;
    }

    public void setCircuitService(ICircuitService circuitService) {
        this.circuitService = circuitService;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public IZoneService getZoneService() {
        return zoneService;
    }

    public void setZoneService(IZoneService zoneService) {
        this.zoneService = zoneService;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Zone> getZones() {
        try {
            zones = zoneService.findAllZone();
            return zones;
        } catch (ServiceException ex) {
            Logger.getLogger(CircuitBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }
 
    public List<Circuit> getCircuits() {
        try {
            circuits = circuitService.findAllCircuit();
            return circuits;
        } catch (ServiceException ex) {
            Logger.getLogger(CircuitBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setCircuits(List<Circuit> circuits) {
        this.circuits = circuits;
    }
    
    public void saveOrUpdateZone() throws ServiceException{
        if (circuit != null && circuit.getCode() != null) {
            circuit.setZone(zoneService.findZoneById(Long.valueOf(id)));
            circuitService.saveOrUpdateCircuit(circuit);
            if (circuit.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", circuit.getCode() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", circuit.getCode()+ " a été enregistré"));
            }

            circuit = new Circuit();
        }
        
    }
    
    public void deleteCircuit() throws ServiceException{
        if (circuit != null && circuit.getId() != null) {
            circuitService.deleteCircuit(circuit.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", circuit.getCode() + " a été supprimé"));
            circuit = new Circuit();
        }
    }
    
     public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (circuit != null && circuit.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner unn circuit avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (circuit != null && circuit.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un circuit avant de supprimer "));
        }
    }
    
    public void test(){
        System.out.println("J'ai cliqué sur : ");
        System.out.println(circuit);
    }
    
}
