package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IPlanningDao;
import com.royken.bracongo.survey.dao.IPlanningPdvDao;
import com.royken.bracongo.survey.dao.IPointDeVenteDao;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PlanningPdv;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.service.IPlanningPdvService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class PlanningPdvServiceImpl implements IPlanningPdvService{
    
    @Inject
    private IPlanningPdvDao planningPdvDao;
    
    @Inject
    private IPlanningDao planningDao;
    
    @Inject
    private IPointDeVenteDao pointDeVenteDao;

    public IPointDeVenteDao getPointDeVenteDao() {
        return pointDeVenteDao;
    }

    public void setPointDeVenteDao(IPointDeVenteDao pointDeVenteDao) {
        this.pointDeVenteDao = pointDeVenteDao;
    }

    public IPlanningPdvDao getPlanningPdvDao() {
        return planningPdvDao;
    }

    public void setPlanningPdvDao(IPlanningPdvDao planningPdvDao) {
        this.planningPdvDao = planningPdvDao;
    }

    public IPlanningDao getPlanningDao() {
        return planningDao;
    }

    public void setPlanningDao(IPlanningDao planningDao) {
        this.planningDao = planningDao;
    }

    @Override
    public PlanningPdv saveOrUpdatePlanningPdv(PlanningPdv planningPdv) throws ServiceException {       
        try {
            if(planningPdv.getId() == null){    
                planningPdv.setActive(1);
                return planningPdvDao.create(planningPdv);
                }
            else{
                planningPdv.setActive(1);
                return planningPdvDao.update(planningPdv);
            }
            } catch (DataAccessException ex) {
                Logger.getLogger(PlanningPdvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }

    @Override
    public void deletePlanningPdv(Long idPpdv) throws ServiceException {
        try {
            PlanningPdv planningPdv = planningPdvDao.findById(idPpdv);
            if(planningPdv != null){
                planningPdvDao.delete(planningPdv);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PlanningPdvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<PlanningPdv> findByPlanning(Long idPlanning) throws ServiceException {
        try {
            Planning planning = planningDao.findById(idPlanning);
            if(planning != null){
                return planningPdvDao.findByPlanning(planning);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PlanningPdvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public PlanningPdv save(Long idPlanning, Long idPdv) throws ServiceException {
        try {
            PointDeVente pointDeVente = pointDeVenteDao.findById(idPdv);
            Planning planning = planningDao.findById(idPlanning);
            PlanningPdv planningPdv = new PlanningPdv();
            planningPdv.setPlanning(planning);
            planningPdv.setPointDeVente(pointDeVente);
            planningPdv.setActive(1);
            planningPdvDao.create(planningPdv);
        } catch (DataAccessException ex) {
            Logger.getLogger(PlanningPdvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
