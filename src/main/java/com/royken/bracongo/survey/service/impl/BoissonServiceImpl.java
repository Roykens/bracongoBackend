package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IBoissonDao;
import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.service.IBoissonService;
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
public class BoissonServiceImpl implements IBoissonService {

    @Inject
    private IBoissonDao boissonDao;

    public IBoissonDao getBoissonDao() {
        return boissonDao;
    }

    public void setBoissonDao(IBoissonDao boissonDao) {
        this.boissonDao = boissonDao;
    }

    @Override
    public Boisson saveOrUpdateBoisson(Boisson boisson) throws ServiceException {
        try {
            if (boisson.getId() == null) {
                boisson.setActive(1);
                return boissonDao.create(boisson);
            }
            else{
                return boissonDao.update(boisson);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger("Log").log(Level.SEVERE, ex.toString());
            throw new ServiceException("Opération impossible");
        }
        
    }

    @Override
    public Boisson findBoissonById(Long id) throws ServiceException {
        try {
            return boissonDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger("Log").log(Level.SEVERE, ex.toString());
            throw new ServiceException("Boisson non trouvée");
        }
    }

    @Override
    public void deleteBoisson(Long id) throws ServiceException {
        try {
            Boisson boisson = boissonDao.findById(id);
            if(boisson != null){
                boisson.setActive(0);
                boissonDao.update(boisson);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(BoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Boisson> findAllBoisson() throws ServiceException {
        try {
            return boissonDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(BoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Boisson> findBoissonByEnterpriseAndType(boolean isBracongo, TypeBoisson typeBoisson) throws ServiceException {
        try {
            return boissonDao.findAllByEnterpriseAndType(isBracongo, typeBoisson);
        } catch (DataAccessException ex) {
            Logger.getLogger(BoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
