package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IDisponibiliteBoissonDao;
import com.royken.bracongo.survey.entities.DisponibiliteBoisson;
import com.royken.bracongo.survey.service.IDisponibiliteBoissonService;
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
public class DisponibiliteBoissonServiceImpl implements IDisponibiliteBoissonService {

    @Inject
    private IDisponibiliteBoissonDao disponibiliteBoissonDao;

    public IDisponibiliteBoissonDao getDisponibiliteBoissonDao() {
        return disponibiliteBoissonDao;
    }

    public void setDisponibiliteBoissonDao(IDisponibiliteBoissonDao disponibiliteBoissonDao) {
        this.disponibiliteBoissonDao = disponibiliteBoissonDao;
    }

    @Override
    public DisponibiliteBoisson saveOrUpdateDisponibiliteBoisson(DisponibiliteBoisson disponibiliteBoisson) throws ServiceException {
        try {
            if (disponibiliteBoisson.getId() == null) {
                return disponibiliteBoissonDao.create(disponibiliteBoisson);
            } else {
                return disponibiliteBoissonDao.update(disponibiliteBoisson);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(DisponibiliteBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public DisponibiliteBoisson findDisponibiliteById(Long id) throws ServiceException {
        try {
            return disponibiliteBoissonDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(DisponibiliteBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteDisponibilite(Long id) throws ServiceException {
        try {
            DisponibiliteBoisson db = disponibiliteBoissonDao.findById(id);
            if(db != null){
                disponibiliteBoissonDao.delete(db);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(DisponibiliteBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<DisponibiliteBoisson> findAllDisponibilite() throws ServiceException {
        try {
            return disponibiliteBoissonDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(DisponibiliteBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
