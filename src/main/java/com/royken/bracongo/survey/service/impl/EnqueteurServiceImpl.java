package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IEnqueteurDao;
import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.service.IEnqueteurService;
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
public class EnqueteurServiceImpl implements IEnqueteurService {

    @Inject
    private IEnqueteurDao enqueteurDao;

    public IEnqueteurDao getEnqueteurDao() {
        return enqueteurDao;
    }

    public void setEnqueteurDao(IEnqueteurDao enqueteurDao) {
        this.enqueteurDao = enqueteurDao;
    }

    @Override
    public Enqueteur saveOrUpdateEnqueteur(Enqueteur enqueteur) throws ServiceException {
        try {
            if (enqueteur.getId() == null) {
                return enqueteurDao.create(enqueteur);
            }
            else{
                return enqueteurDao.update(enqueteur);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EnqueteurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
         return null;
    }

    @Override
    public Enqueteur findEnqueteurById(Long id) throws ServiceException {
        try {
            return enqueteurDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(EnqueteurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Enqueteur> findAllEnqueteur() throws ServiceException {
        try {
            return enqueteurDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(EnqueteurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void deleteEnqueteur(Long id) throws ServiceException {
        try {
            Enqueteur enqueteur = enqueteurDao.findById(id);
            if(enqueteur != null){
                enqueteurDao.delete(enqueteur);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(EnqueteurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Enqueteur findByLoginAndPassord(String login, String password) throws ServiceException {
        try {
            return enqueteurDao.findEnqueteurByUsernameAndPassword(login, password);
        } catch (DataAccessException ex) {
            Logger.getLogger(EnqueteurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
