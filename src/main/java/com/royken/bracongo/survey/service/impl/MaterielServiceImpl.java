package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IMaterielDao;
import com.royken.bracongo.survey.entities.Materiel;
import com.royken.bracongo.survey.service.IMaterielService;
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
public class MaterielServiceImpl implements IMaterielService {

    @Inject
    private IMaterielDao materielDao;

    public IMaterielDao getMaterielDao() {
        return materielDao;
    }

    public void setMaterielDao(IMaterielDao materielDao) {
        this.materielDao = materielDao;
    }

    @Override
    public Materiel saveOrUpdateMateriel(Materiel materiel) throws ServiceException {
        try {
            if (materiel.getId() == null) {
                return materielDao.create(materiel);
            } else {
                return materielDao.update(materiel);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(MaterielServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Materiel findMaterielById(Long id) throws ServiceException {
        try {
            return materielDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(MaterielServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteMateriel(Long id) throws ServiceException {
        try {
            Materiel materiel = materielDao.findById(id);
            if(materiel != null){
                materielDao.delete(materiel);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(MaterielServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Materiel> findAllMateriel() throws ServiceException {
        try {
            return materielDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(MaterielServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
