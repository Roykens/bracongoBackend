package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.ITelephoneDao;
import com.royken.bracongo.survey.entities.Telephone;
import com.royken.bracongo.survey.service.ITelephoneService;
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
public class TelephoneServiceImpl implements ITelephoneService {

    @Inject
    private ITelephoneDao telephoneDao;

    public ITelephoneDao getTelephoneDao() {
        return telephoneDao;
    }

    public void setTelephoneDao(ITelephoneDao telephoneDao) {
        this.telephoneDao = telephoneDao;
    }

    @Override
    public Telephone saveOrUpdateTelephone(Telephone telephone) throws ServiceException {
        try {
            if (telephone.getId() == null) {
                return telephoneDao.create(telephone);
            } else {
                return telephoneDao.update(telephone);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(TelephoneServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Telephone findTelephoneById(Long id) throws ServiceException {
        try {
            return telephoneDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(TelephoneServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteTelephone(Long id) throws ServiceException {
        try {
            Telephone telephone = telephoneDao.findById(id);
            if(telephone != null){
                telephoneDao.delete(telephone);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(TelephoneServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Telephone> findAllTelephone() throws ServiceException {
        try {
            return telephoneDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(TelephoneServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
