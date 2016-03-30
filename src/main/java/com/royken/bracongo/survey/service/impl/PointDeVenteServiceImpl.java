package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IEnqueteurDao;
import com.royken.bracongo.survey.dao.IPointDeVenteDao;
import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.service.IPointDeVenteService;
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
public class PointDeVenteServiceImpl implements IPointDeVenteService {

    @Inject
    private IPointDeVenteDao pointDeVenteDao;
    
    @Inject
    private IEnqueteurDao enqueteurDao;

    public IPointDeVenteDao getPointDeVenteDao() {
        return pointDeVenteDao;
    }

    public void setPointDeVenteDao(IPointDeVenteDao pointDeVenteDao) {
        this.pointDeVenteDao = pointDeVenteDao;
    }

    public IEnqueteurDao getEnqueteurDao() {
        return enqueteurDao;
    }

    public void setEnqueteurDao(IEnqueteurDao enqueteurDao) {
        this.enqueteurDao = enqueteurDao;
    }
    

    @Override
    public PointDeVente saveOrUpdatePDV(PointDeVente pointDeVente) throws ServiceException {

        try {
            if (pointDeVente.getId() == null) {
                return pointDeVenteDao.create(pointDeVente);
            }
            else{
                return pointDeVenteDao.update(pointDeVente);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public PointDeVente findPDVById(Long id) throws ServiceException {
        try {
            return pointDeVenteDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<PointDeVente> findAllPointDeVente() throws ServiceException {
        try {
            return pointDeVenteDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void deletePDV(Long id) throws ServiceException {
        try {
            PointDeVente pointDeVente = pointDeVenteDao.findById(id);
            if(pointDeVente != null){
                pointDeVenteDao.delete(pointDeVente);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<PointDeVente> findAllByEnqueteur(Long idEnqueteur) throws ServiceException {
        try {
            Enqueteur enqueteur = enqueteurDao.findById(idEnqueteur);
            if(enqueteur != null){
                System.out.println("Le secteur");
                System.out.println(enqueteur.getSecteur());
                return pointDeVenteDao.findByEnqueteur(enqueteur);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
