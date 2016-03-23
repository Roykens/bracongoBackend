package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IPlvDao;
import com.royken.bracongo.survey.entities.PLV;
import com.royken.bracongo.survey.service.IPlvService;
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
public class PlvServiceImpl implements IPlvService {

    @Inject
    private IPlvDao plvDao;

    public IPlvDao getPlvDao() {
        return plvDao;
    }

    public void setPlvDao(IPlvDao plvDao) {
        this.plvDao = plvDao;
    }

    @Override
    public PLV saveOrUpdatePlv(PLV plv) throws ServiceException {
        try {
            if (plv.getId() == null) {
                return plvDao.create(plv);
            } else {
                return plvDao.update(plv);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PlvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public PLV findByPlvId(Long id) throws ServiceException {
        try {
            return plvDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(PlvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deletePlv(Long id) throws ServiceException {
        try {
            PLV plv = plvDao.findById(id);
            if(plv != null){
                plvDao.delete(plv);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PlvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<PLV> findAllPlv() throws ServiceException {
        try {
            return plvDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(PlvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
