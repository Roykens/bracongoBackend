package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IsecteurDao;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.service.ISecteurService;
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
public class SecteurServiceImpl implements ISecteurService {

    @Inject
    private IsecteurDao isecteurDao;

    public IsecteurDao getIsecteurDao() {
        return isecteurDao;
    }

    public void setIsecteurDao(IsecteurDao isecteurDao) {
        this.isecteurDao = isecteurDao;
    }

    @Override
    public Secteur saveOrUpdateSecteur(Secteur secteur) throws ServiceException {
        try {
            if (secteur.getId() == null) {
                System.out.println("\n --- \n totototototot \n \n");
                return isecteurDao.create(secteur);
            } else {
                return isecteurDao.update(secteur);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(SecteurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Secteur findSecteurById(Long id) throws ServiceException {
        try {
            return isecteurDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(SecteurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteSecteur(Long id) throws ServiceException {
        try {
            Secteur secteur = isecteurDao.findById(id);
            if(secteur != null){
                isecteurDao.delete(secteur);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(SecteurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Secteur> findAllSecteur() throws ServiceException {
        try {
            return isecteurDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(SecteurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
