package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Format;
import com.royken.bracongo.survey.service.IFormatService;
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
@Named(value = "formatBean")
@RequestScoped
public class FormatBean implements Serializable{
    
    @EJB
    private IFormatService formatService;
    
    private Format format = new Format();
    
    private List<Format> formats;

    /**
     * Creates a new instance of Format
     */
    public FormatBean() {
    }

    public IFormatService getFormatService() {
        return formatService;
    }

    public void setFormatService(IFormatService formatService) {
        this.formatService = formatService;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public List<Format> getFormats() {
        try {
            formats = formatService.findAllFormat();
            return formats;
        } catch (ServiceException ex) {
            Logger.getLogger(FormatBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }
    
    
    public void saveOrUpdateZone() throws ServiceException{
        if (format != null && format.getVolume() > 0) {
            formatService.saveOrUpdateFormat(format);
            if (format.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", "Le format " +format.getVolume() + "cl a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", "Le format " +format.getVolume()+ "cl a été enregistré"));
            }

            format = new Format();
        }
        
    }
    
    public void deleteFormat() throws ServiceException{
        if (format != null && format.getId() != null) {
            formatService.deleteFormat(format.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", format.getVolume() + "cl a été supprimé"));
            format = new Format();
        }
    }
    
     public void verifierEtUpdate(ActionEvent actionEvent) throws ServiceException {
        if (format != null && format.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgUpdate').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un format avant de modifier "));
        }
    }

    public void verifierEtSupprimer(ActionEvent actionEvent) throws ServiceException {
        if (format != null && format.getId() != null) {
            RequestContext.getCurrentInstance().execute("PF('confirmation').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "selectionner un format avant de supprimer "));
        }
    }
    
    public void test(){
        System.out.println("J'ai cliqué sur : ");
        System.out.println(format);
    }
}
