package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.Format;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.service.IBoissonService;
import com.royken.bracongo.survey.service.IFormatBoissonService;
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
@Named(value = "boissonBean")
@RequestScoped
public class BoissonBean implements Serializable {

    @EJB
    private IBoissonService boissonService;

    @EJB
    private IFormatService iFormatService;

    @EJB
    private IFormatBoissonService formatBoissonService;

    private Boisson boisson = new Boisson();

    private List<FormatBoisson> formatBoissons;

    private FormatBoisson formatBoisson = new FormatBoisson();
    
    private Format format = new Format();

    private List<Boisson> boissons;

    private List<Boisson> bgBracongo;

    private List<Boisson> bgBralima;

    private List<Boisson> biBracongo;

    private List<Boisson> biBralima;

    private List<Format> formats;

    private Long idFormat;

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

    public FormatBoisson getFormatBoisson() {
        return formatBoisson;
    }

    public void setFormatBoisson(FormatBoisson formatBoisson) {
        this.formatBoisson = formatBoisson;
    }

    public List<Boisson> getBoissons() {
        return boissons;
    }

    public void setBoissons(List<Boisson> boissons) {
        this.boissons = boissons;
    }

    public IFormatService getiFormatService() {
        return iFormatService;
    }

    public void setiFormatService(IFormatService iFormatService) {
        this.iFormatService = iFormatService;
    }

    public IFormatBoissonService getFormatBoissonService() {
        return formatBoissonService;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }
    
    public void setFormatBoissonService(IFormatBoissonService formatBoissonService) {
        this.formatBoissonService = formatBoissonService;
    }

    public List<Boisson> getBgBracongo() {
        try {
            bgBracongo = boissonService.findBoissonByEnterpriseAndType(true, TypeBoisson.BG);
            return bgBracongo;
        } catch (ServiceException ex) {
            Logger.getLogger(BoissonBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setBgBracongo(List<Boisson> bgBracongo) {
        this.bgBracongo = bgBracongo;
    }

    public List<Boisson> getBgBralima() {
        try {
            bgBralima = boissonService.findBoissonByEnterpriseAndType(false, TypeBoisson.BG);
            return bgBralima;
        } catch (ServiceException ex) {
            Logger.getLogger(BoissonBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setBgBralima(List<Boisson> bgBralima) {
        this.bgBralima = bgBralima;
    }

    public List<Boisson> getBiBracongo() {
        try {
            biBracongo = boissonService.findBoissonByEnterpriseAndType(true, TypeBoisson.BI);
            return biBracongo;
        } catch (ServiceException ex) {
            Logger.getLogger(BoissonBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setBiBracongo(List<Boisson> biBracongo) {
        this.biBracongo = biBracongo;
    }

    public List<Boisson> getBiBralima() {
        try {
            biBralima = boissonService.findBoissonByEnterpriseAndType(false, TypeBoisson.BI);
            return biBralima;
        } catch (ServiceException ex) {
            Logger.getLogger(BoissonBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setBiBralima(List<Boisson> biBralima) {
        this.biBralima = biBralima;
    }

    public List<FormatBoisson> getFormatBoissons(Boisson boisson) {
        try {
            System.out.println("================\n==========\n=============\n");
            System.out.println("La boisson "+boisson);
            System.out.println("================\n==========\n=============\n");
            formatBoissons = formatBoissonService.findByBoisson(701L);
            return formatBoissons;
        } catch (ServiceException ex) {
            Logger.getLogger(BoissonBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setFormatBoissons(List<FormatBoisson> formatBoissons) {
        this.formatBoissons = formatBoissons;
    }

    public List<Format> getFormats() {
        try {
            formats = iFormatService.findAllFormat();
            return formats;
        } catch (ServiceException ex) {
            Logger.getLogger(BoissonBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    public Long getIdFormat() {
        return idFormat;
    }

    public void setIdFormat(Long idFormat) {
        this.idFormat = idFormat;
    }

    public void saveOrUpdateBoisson() throws ServiceException {
        System.out.println("La boisson");
        System.out.println(boisson);
        if (boisson != null && boisson.getNom() != null) {
            boisson.setIsBracongo(true);
            boisson.setTypeBoisson(TypeBoisson.BI);
            boissonService.saveOrUpdateBoisson(boisson);
            if (boisson.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", boisson.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", boisson.getNom() + " a été enregistré"));
            }

            boisson = new Boisson();
        }
    }
    
    public void saveOrUpdateBgBracongo() throws ServiceException {
        System.out.println("La boisson");
        System.out.println(boisson);
        if (boisson != null && boisson.getNom() != null) {
            boisson.setIsBracongo(true);
            boisson.setTypeBoisson(TypeBoisson.BG);
            boissonService.saveOrUpdateBoisson(boisson);
            if (boisson.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", boisson.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", boisson.getNom() + " a été enregistré"));
            }

            boisson = new Boisson();
        }
    }
    
    public void saveOrUpdateBiBralima() throws ServiceException {
        System.out.println("La boisson");
        System.out.println(boisson);
        if (boisson != null && boisson.getNom() != null) {
            boisson.setIsBracongo(false);
            boisson.setTypeBoisson(TypeBoisson.BI);
            boissonService.saveOrUpdateBoisson(boisson);
            if (boisson.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", boisson.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", boisson.getNom() + " a été enregistré"));
            }

            boisson = new Boisson();
        }
    }
    
    public void saveOrUpdateBgBralima() throws ServiceException {
        System.out.println("La boisson");
        System.out.println(boisson);
        if (boisson != null && boisson.getNom() != null) {
            boisson.setIsBracongo(false);
            boisson.setTypeBoisson(TypeBoisson.BG);
            boissonService.saveOrUpdateBoisson(boisson);
            if (boisson.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", boisson.getNom() + " a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", boisson.getNom() + " a été enregistré"));
            }

            boisson = new Boisson();
        }
    }

    public void deleteBoisson() throws ServiceException {
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

    public void saveFormatBoisson() {
        System.out.println("Le format Boisson");
        System.out.println(boisson);
        try {
            FormatBoisson formatBoisson1 = new FormatBoisson();
            formatBoisson1.setBoisson(boisson);
            formatBoisson1.setFormat(iFormatService.findFormatById(idFormat));
            formatBoissonService.saveOrUpdateFormatBoisson(formatBoisson1);
            idFormat = 0L;
            boisson = new Boisson();

        } catch (ServiceException ex) {
            Logger.getLogger(BoissonBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        idFormat = 0L;
        boisson = new Boisson();
    }
    
    public void deleteFormatBoisson(){
        
        
        
    }

}
