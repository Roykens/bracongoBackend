package com.royken.bracongo.survey.resource.impl;

import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.projection.PlanningEnquetteur;
import com.royken.bracongo.survey.resource.IPointDeVenteResource;
import com.royken.bracongo.survey.service.IPlanningService;
import com.royken.bracongo.survey.service.IPointDeVenteService;
import com.royken.bracongo.survey.service.ServiceException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/pdv")
public class PointDeVenteResourceImpl implements IPointDeVenteResource{
    
    @EJB
    private IPointDeVenteService pointDeVenteService;
    
    @EJB
    private IPlanningService planningService;

    public IPointDeVenteService getPointDeVenteService() {
        return pointDeVenteService;
    }

    public void setPointDeVenteService(IPointDeVenteService pointDeVenteService) {
        this.pointDeVenteService = pointDeVenteService;
    }
    
    

    @Override
    public List<PointDeVente> getAllPointDeVente() {
        try {
            return pointDeVenteService.findAllPointDeVente();
        } catch (ServiceException ex) {
            Logger.getLogger(PointDeVenteResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public PlanningEnquetteur getAllPointDeVenteByEnqueteur(long id) {
        PlanningEnquetteur planningEnquetteur = new PlanningEnquetteur();
        try {
            Planning planning = planningService.findByEnqueteur(id);
            if(planning != null){
                planningEnquetteur.setIdPlanning(planning.getId());
                List<PointDeVente> pointDeVentes = pointDeVenteService.findByPlanningAndEnqueteur(id);
                planningEnquetteur.setPointDeVentes(pointDeVentes);
                return planningEnquetteur;
            }
            //return pointDeVenteService.findAllByEnqueteur(id);
        } catch (ServiceException ex) {
            Logger.getLogger(PointDeVenteResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return planningEnquetteur;
    }

    @Override
    public PlanningEnquetteur getAllPointDeVenteByEnqueteur(String login, String password) {
        try {
            PlanningEnquetteur pe = pointDeVenteService.getAllPointDeVenteByEnqueteur(login, password);
           // System.out.println(pe);
            return pe;
        } catch (ServiceException ex) {
            Logger.getLogger(PointDeVenteResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new PlanningEnquetteur();
    }
    
}
