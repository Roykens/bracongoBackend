package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IEtatPlvDao;
import com.royken.bracongo.survey.entities.EtatPlv;
import com.royken.bracongo.survey.service.IEtatPlvService;
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
public class EtatPlvServiceImpl implements IEtatPlvService {

    @Inject
    private IEtatPlvDao etatPlvDao;

    public IEtatPlvDao getEtatPlvDao() {
        return etatPlvDao;
    }

    public void setEtatPlvDao(IEtatPlvDao etatPlvDao) {
        this.etatPlvDao = etatPlvDao;
    }

    @Override
    public EtatPlv saveOrUpdateEtatPlv(EtatPlv etatPlv) throws ServiceException {
        try {
            if (etatPlv.getId() == null) {
                return etatPlvDao.create(etatPlv);
            } else {
                return etatPlvDao.update(etatPlv);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EtatPlvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public EtatPlv findEtatPlvById(Long id) throws ServiceException {
        try {
            return etatPlvDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EtatPlvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<EtatPlv> findAllEtatPlv() throws ServiceException {
        try {
            return etatPlvDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(EtatPlvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void deletetatPlv(Long id) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
