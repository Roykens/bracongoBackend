package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.service.ICircuitService;
import com.royken.bracongo.survey.service.IPointDeVenteService;
import com.royken.bracongo.survey.service.ServiceException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "pointDeVenteBean")
@RequestScoped
public class PointDeVenteBean  implements Serializable{
    
    private List<PointDeVente> pointDeVntes;
    
    private UploadedFile file;
    
    private PointDeVente pointDeVente = new PointDeVente();
    
    private List<Circuit> circuits;
    
    private String id;

    @EJB
    private IPointDeVenteService pointDeVenteService;
    
    @EJB
    private ICircuitService circuitService;
    
    /**
     * Creates a new instance of PointDeVenteBean
     */
    public PointDeVenteBean() {
    }

    public List<PointDeVente> getPointDeVntes() {
        try {
            pointDeVntes = pointDeVenteService.findAllPointDeVente();
            return pointDeVntes;
        } catch (ServiceException ex) {
            Logger.getLogger(PointDeVenteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public List<Circuit> getCircuits() {
        try {
            circuits = circuitService.findAllCircuit();
            return circuits;
        } catch (ServiceException ex) {
            Logger.getLogger(PointDeVenteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setCircuits(List<Circuit> circuits) {
        this.circuits = circuits;
    }

    public void setPointDeVntes(List<PointDeVente> pointDeVntes) {
        this.pointDeVntes = pointDeVntes;
    }

    public IPointDeVenteService getPointDeVenteService() {
        return pointDeVenteService;
    }

    public void setPointDeVenteService(IPointDeVenteService pointDeVenteService) {
        this.pointDeVenteService = pointDeVenteService;
    }

    public PointDeVente getPointDeVente() {
       
        return pointDeVente;
    }

    public void setPointDeVente(PointDeVente pointDeVente) {
        this.pointDeVente = pointDeVente;
    }

    public ICircuitService getCircuitService() {
        return circuitService;
    }

    public void setCircuitService(ICircuitService circuitService) {
        this.circuitService = circuitService;
    }
    
    

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void saveOrUpdatePDV(){
        System.out.println(pointDeVente);
        try {
            pointDeVente.setCircuit(circuitService.findCircuitById(Long.valueOf(id)));
            pointDeVenteService.saveOrUpdatePDV(pointDeVente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération reussie", pointDeVente.getNom() + " a été enregistré "));
        } catch (ServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Echec opération", pointDeVente.getNom() + " n'a pas été enregistré "));
            Logger.getLogger(PointDeVenteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        pointDeVente = new PointDeVente();
    }
    
    public void deletePvd(){
        try {
            pointDeVenteService.deletePDV(pointDeVente.getId());
        } catch (ServiceException ex) {
            Logger.getLogger(PointDeVenteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        pointDeVente = new PointDeVente();
    }
    
    public void importer(){
        System.out.println("J'ai clické sur ");
        System.out.println(pointDeVente);   
    }
    
}
