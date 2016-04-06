package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Planning;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IPlanningService {
    
    public Planning saveOrUpdatePlanning(Planning planning) throws ServiceException;
    
    public List<Planning> findAllPlanning() throws ServiceException;
    
    public void deletePlanning(Long id) throws ServiceException;
    
    public Planning findByEnqueteur(Long idEnqueteur) throws ServiceException;
}
