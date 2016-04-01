package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.ICircuitDao;
import com.royken.bracongo.survey.dao.IEnqueteurDao;
import com.royken.bracongo.survey.dao.IPointDeVenteDao;
import com.royken.bracongo.survey.dao.IZoneDao;
import com.royken.bracongo.survey.dao.IsecteurDao;
import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.Zone;
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

    @Inject
    private ICircuitDao circuitDao;

    @Inject
    private IZoneDao zoneDao;

    @Inject
    private IsecteurDao secteurDao;

    public ICircuitDao getCircuitDao() {
        return circuitDao;
    }

    public void setCircuitDao(ICircuitDao circuitDao) {
        this.circuitDao = circuitDao;
    }

    public IZoneDao getZoneDao() {
        return zoneDao;
    }

    public void setZoneDao(IZoneDao zoneDao) {
        this.zoneDao = zoneDao;
    }

    public IsecteurDao getSecteurDao() {
        return secteurDao;
    }

    public void setSecteurDao(IsecteurDao secteurDao) {
        this.secteurDao = secteurDao;
    }

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
            } else {
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
            if (pointDeVente != null) {
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
            if (enqueteur != null) {
                System.out.println("L'enqueteur");
                System.out.println(enqueteur);
                System.out.println(enqueteur.getSecteur());
                return pointDeVenteDao.findByEnqueteur(enqueteur);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<PointDeVente> findByCriteria(Long idSecteur, Long idZone, Long idCircuit) throws ServiceException {
        try {
            Secteur secteur = null;
            Zone zone = null;
            Circuit circuit = null;

            if (idCircuit > 0) {
                circuit = circuitDao.findById(idCircuit);
            }
            
            if(idZone > 0){
                zone = zoneDao.findById(idZone);
            }
            
            if(idSecteur > 0){
                secteur = secteurDao.findById(idSecteur);
            }
            
            return pointDeVenteDao.findBySecteurZoneCircuit(secteur, zone, circuit);
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
