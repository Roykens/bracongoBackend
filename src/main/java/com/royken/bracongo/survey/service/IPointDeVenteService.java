package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.projection.PlanningEnquetteur;
import com.royken.bracongo.survey.service.util.ImportationResult;
import java.io.InputStream;
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
    
    public ImportationResult importPdv(InputStream stream, Long idCircuit) throws ServiceException;
    
    public List<PointDeVente> findByPlanningAndEnqueteur(Long idEnqueteur) throws ServiceException;
    
    public PlanningEnquetteur getAllPointDeVenteByEnqueteur(String login, String password) throws ServiceException;
    
  //  public List<PointDeVente> findByPlanningAndEnqueteur(String ) throws ServiceException;
}
