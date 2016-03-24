package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.PointDeVente;
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
import javax.enterprise.context.SessionScoped;
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

    @EJB
    private IPointDeVenteService pointDeVenteService;
    
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
    
    public void saveOrUpdatePDV(){
        System.out.println(pointDeVente);
        try {
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
