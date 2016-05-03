package com.royken.bracongo.survey.resource.impl;

import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.bracongo.survey.entities.projection.ReponseProjection;
import com.royken.bracongo.survey.resource.IReponseResource;
import com.royken.bracongo.survey.service.IReponseService;
import com.royken.bracongo.survey.service.ServiceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/reponse")
public class ReponseResourceImpl implements IReponseResource{
    
    @EJB
    private IReponseService reponseService;

    public IReponseService getReponseService() {
        return reponseService;
    }

    public void setReponseService(IReponseService reponseService) {
        this.reponseService = reponseService;
    }
    
    

   @Override
    public void createAnnee(ReponseProjection projection) {
        try {
            System.out.println("Je suis ici");
            System.out.println(projection);
            reponseService.saveReponseProjection(projection);
           
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (ServiceException ex) {
            Logger.getLogger(ReponseResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  /*  @Override
    public Toto insert(Toto toto) {
        System.out.println(toto);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }*/

    @Override
    public int zozo() {
        return reponseService.countReponseByCriteria(null, null, null, null, null);
    }

    @Override
    public int tata() {
        try {
            return reponseService.countDisponibiliteFormat(null, Boolean.TRUE, Boolean.TRUE, null, null);
        } catch (ServiceException ex) {
            Logger.getLogger(ReponseResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}
