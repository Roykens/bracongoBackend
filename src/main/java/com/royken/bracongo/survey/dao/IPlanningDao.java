package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IPlanningDao extends IGenericDao<Planning, Long>{
    
    /**
     * Retourne le planning d'un enqueteur
     * @param enqueteur l'enqueteur
     * @return le planning
     * @throws DataAccessException 
     */
    public Planning getByEnqueteur(Enqueteur enqueteur) throws DataAccessException; 
    
    /**
     * Retourne la liste des plannings
     * @returncla liste
     * @throws DataAccessException 
     */
    public List<Planning> findAllActive() throws DataAccessException;
    
    public Planning getByEnqueteurDate(Date date , Enqueteur enqueteur ) throws DataAccessException;
            
    
}
