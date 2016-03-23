package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IPrixBoissonDao;
import com.royken.bracongo.survey.entities.PrixBoisson;
import com.royken.bracongo.survey.service.IPrixBoissonService;
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
public class PrixBoissonServiceImpl implements IPrixBoissonService {

    @Inject
    private IPrixBoissonDao boissonDao;

    public IPrixBoissonDao getBoissonDao() {
        return boissonDao;
    }

    public void setBoissonDao(IPrixBoissonDao boissonDao) {
        this.boissonDao = boissonDao;
    }

    @Override
    public PrixBoisson saveOrUpdatePrixBoisson(PrixBoisson prixBoisson) throws ServiceException {
        try {
            if (prixBoisson.getId() == null) {
                return boissonDao.create(prixBoisson);
            } else {
                return boissonDao.update(prixBoisson);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PrixBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public PrixBoisson findPrixBoissonById(Long id) throws ServiceException {
        try {
            return boissonDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(PrixBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deletePrixBoisson(Long id) throws ServiceException {
        try {
            PrixBoisson pb = boissonDao.findById(id);
            if(pb != null){
                boissonDao.delete(pb);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PrixBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<PrixBoisson> findAllPrixBoisson() throws ServiceException {
        try {
            return boissonDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(PrixBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
