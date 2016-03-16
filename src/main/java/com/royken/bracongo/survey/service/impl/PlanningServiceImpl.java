package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IPlanningDao;
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

    @Override
    public Planning saveOrUpdatePlanning(Planning planning) throws ServiceException {
        try {
            if (planning.getId() == null) {
                return planningDao.create(planning);
            }
            else{
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
            return planningDao.findAll();
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

}
