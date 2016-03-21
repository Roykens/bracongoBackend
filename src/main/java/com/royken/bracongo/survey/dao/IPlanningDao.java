package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IPlanningDao extends IGenericDao<Planning, Long>{
    
    public Planning getByEnqueteur(Enqueteur enqueteur) throws DataAccessException; 
            
    
}
