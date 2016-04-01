package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PlanningPdv;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.service.IEnqueteurService;
import com.royken.bracongo.survey.service.IPlanningPdvService;
import com.royken.bracongo.survey.service.IPlanningService;
import com.royken.bracongo.survey.service.IPointDeVenteService;
import com.royken.bracongo.survey.service.ServiceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
public class PlanningBean implements Serializable {

    @EJB
    private IPlanningService planningService;

    @EJB
    private IPlanningPdvService planningPdvService;

    @EJB
    private IPointDeVenteService pointDeVenteService;

    @EJB
    private IEnqueteurService enqueteurService;

    private List<Enqueteur> enqueteurs;

    private Planning planning = new Planning();

    private List<Planning> plannings;

    private List<PointDeVente> pointDeVentes;

    private List<PlanningPdv> planningPdvs;

    private PlanningPdv planningPdv = new PlanningPdv();

    private List<PointDeVente> pointDeVenteschoisi;
    
    private List<String> pdvchoisi;

    private Long idEnqueteur;

    private List<Long> idPointDeVente;

    /**
     * Creates a new instance of PlanningBean
     */
    public PlanningBean() {
    }

    public List<PointDeVente> getPointDeVentes() {
        try {
            pointDeVentes = pointDeVenteService.findAllPointDeVente();
            return pointDeVentes;
        } catch (ServiceException ex) {
            Logger.getLogger(PlanningBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
    
    

    public List<String> getPdvchoisi() {
        return pdvchoisi;
    }

    public void setPdvchoisi(List<String> pdvchoisi) {
        this.pdvchoisi = pdvchoisi;
    }

    
    
    public List<PointDeVente> getPointDeVenteschoisi() {
        return pointDeVenteschoisi;
    }

    public void setPointDeVenteschoisi(List<PointDeVente> pointDeVenteschoisi) {
        this.pointDeVenteschoisi = pointDeVenteschoisi;
    }

    public IEnqueteurService getEnqueteurService() {
        return enqueteurService;
    }

    public void setEnqueteurService(IEnqueteurService enqueteurService) {
        this.enqueteurService = enqueteurService;
    }

    public PlanningPdv getPlanningPdv() {
        return planningPdv;
    }

    public void setPlanningPdv(PlanningPdv planningPdv) {
        this.planningPdv = planningPdv;
    }

    public List<Enqueteur> getEnqueteurs() {
        try {
            enqueteurs = enqueteurService.findAllEnqueteur();
            return enqueteurs;
        } catch (ServiceException ex) {
            Logger.getLogger(PlanningBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setEnqueteurs(List<Enqueteur> enqueteurs) {
        this.enqueteurs = enqueteurs;
    }

    public IPlanningPdvService getPlanningPdvService() {
        return planningPdvService;
    }

    public void setPlanningPdvService(IPlanningPdvService planningPdvService) {
        this.planningPdvService = planningPdvService;
    }

    public IPointDeVenteService getPointDeVenteService() {
        return pointDeVenteService;
    }

    public void setPointDeVenteService(IPointDeVenteService pointDeVenteService) {
        this.pointDeVenteService = pointDeVenteService;
    }

//    public List<PointDeVente> getPointDeVentes() {
//        try {
//            System.out.println("Le planning");
//            System.out.println(planning);
//            pointDeVentes = pointDeVenteService.findAllByEnqueteur(planning.getEnqueteur().getId());
//            return pointDeVentes;
//        } catch (ServiceException ex) {
//            Logger.getLogger(PlanningBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return Collections.EMPTY_LIST;
//    }

    public List<Long> getIdPointDeVente() {
        return idPointDeVente;
    }

    public void setIdPointDeVente(List<Long> idPointDeVente) {
        this.idPointDeVente = idPointDeVente;
    }

    public void setPointDeVentes(List<PointDeVente> pointDeVentes) {
        this.pointDeVentes = pointDeVentes;
    }

    public Long getIdEnqueteur() {
        return idEnqueteur;
    }

    public void setIdEnqueteur(Long idEnqueteur) {
        this.idEnqueteur = idEnqueteur;
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

    public List<PlanningPdv> getPlanningPdvs(Planning planning2) {
        try {
            System.out.println("Le planning pour la liste des pdvs");
            System.out.println(planning2);
            planningPdvs = planningPdvService.findByPlanning(planning2.getId());
            return planningPdvs;
        } catch (ServiceException ex) {
            Logger.getLogger(PlanningBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public List<PlanningPdv> getPlanningPdv2() {
        if(planning != null && planning.getId()!= null){   
        try {
            
                System.out.println("Le planning pour la lsite des pdv2");
                System.out.println(planning);
                planningPdvs = planningPdvService.findByPlanning(planning.getId());
                return planningPdvs;
            
        } catch (ServiceException ex) {
            Logger.getLogger(PlanningBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        return Collections.EMPTY_LIST;
    }

    public void setPlanningPdvs(List<PlanningPdv> planningPdvs) {
        this.planningPdvs = planningPdvs;
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

    public void saveOrUpdatePlanninge() throws ServiceException {
        System.out.println("Le planning de save");
        System.out.println(planning);
        if (planning != null) {
            planning.setDatePlaning(new Date());
            planning.setEnqueteur(enqueteurService.findEnqueteurById(idEnqueteur));
            planningService.saveOrUpdatePlanning(planning);
            if (planning.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation reussie", "Le planning a été mis à jour "));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation reussie", "Le planning a été enregistré"));
            }

            planning = new Planning();
            idEnqueteur = 0L;
        }
    }

    public void deletePlanning() throws ServiceException {
        if (planning != null && planning.getId() != null) {
            planningService.deletePlanning(planning.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Operation reussie", " Planning  supprimé"));
            planning = new Planning();
        }
    }

    public void savePointDeVente() throws ServiceException {
        System.out.println(pdvchoisi);
        System.out.println("Le planning du pdv");
        System.out.println(planning);
        System.out.println("J'ai choisi");
        System.out.println(pdvchoisi);
//        for (Long id : idPointDeVente) {
//            //PlanningPdv planningPdv = new PlanningPdv();
//           planningPdvService.save(planning.getId(), id);
//        }
        
        for (String idpdv : pdvchoisi) {
            planningPdvService.save(planning.getId(), Long.valueOf(idpdv));
        }
        pdvchoisi = new ArrayList<String>();
    }

    public List<PointDeVente> getByEnqueteur() {
        Enqueteur enqueteur = planning.getEnqueteur();
        System.out.println(enqueteur);
        if(planning == null){
            System.out.println("JE SUIS NULLLLLL");
        }
        
        
        System.out.println("Le planning de l'enqueteur");
        System.out.println(planning);
        try {
            return pointDeVenteService.findAllByEnqueteur(planning.getEnqueteur().getId());
        } catch (ServiceException ex) {
            Logger.getLogger(PlanningBean.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        return Collections.EMPTY_LIST;
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

    public void test() {
        System.out.println("J'ai pris le planning");
        System.out.println(planning);
    }
    
    public void terminerPDV(){
        planning = new Planning();
    }

}
