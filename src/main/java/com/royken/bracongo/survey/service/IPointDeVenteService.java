package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.PointDeVente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IPointDeVenteService {
    
    
    public PointDeVente saveOrUpdatePDV(PointDeVente pointDeVente) throws ServiceException;
    
    public PointDeVente findPDVById(Long id) throws ServiceException;
    
    public List<PointDeVente> findAllPointDeVente() throws ServiceException;
    
    public List<PointDeVente> findAllByEnqueteur(Long idEnqueteur) throws ServiceException;
    
    public void deletePDV(Long id) throws ServiceException;
    
    public List<PointDeVente> findByCriteria(Long idSecteur, Long idZone, Long idCircuit) throws ServiceException;
}
