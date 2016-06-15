package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IEnqueteurDao;
import com.royken.bracongo.survey.dao.IPlanningDao;
import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.service.IPlanningService;
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
public class PlanningServiceImpl implements IPlanningService {

    @Inject
    private IPlanningDao planningDao;
    
    @Inject
    private IEnqueteurDao enqueteurDao;

    public IPlanningDao getPlanningDao() {
        return planningDao;
    }

    public void setPlanningDao(IPlanningDao planningDao) {
        this.planningDao = planningDao;
    }

    public IEnqueteurDao getEnqueteurDao() {
        return enqueteurDao;
    }

    public void setEnqueteurDao(IEnqueteurDao enqueteurDao) {
        this.enqueteurDao = enqueteurDao;
    }
    
    

    @Override
    public Planning saveOrUpdatePlanning(Planning planning) throws ServiceException {
        try {
            if (planning.getId() == null) {
                planning.setActive(1);
                return planningDao.create(planning);
            }
            else{
                planning.setActive(1);
                return planningDao.update(planning);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PlanningServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Planning> findAllPlanning() throws ServiceException {
        try {
            return planningDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(PlanningServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void deletePlanning(Long id) throws ServiceException {
        try {
            Planning planning = planningDao.findById(id);
            if(planning != null) {
                planningDao.delete(planning);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PlanningServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Planning findByEnqueteur(Long idEnqueteur) throws ServiceException {
        try {
            Enqueteur enqueteur = enqueteurDao.findById(idEnqueteur);
            return planningDao.getByEnqueteur(enqueteur);
        } catch (DataAccessException ex) {
            Logger.getLogger(PlanningServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Planning findByEnqueteurCredential(String login, String password) throws ServiceException {
        try {
            Enqueteur enqueteur = enqueteurDao.findEnqueteurByUsernameAndPassword(login, password);
            return planningDao.getByEnqueteur(enqueteur);
        } catch (DataAccessException ex) {
            Logger.getLogger(PlanningServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
