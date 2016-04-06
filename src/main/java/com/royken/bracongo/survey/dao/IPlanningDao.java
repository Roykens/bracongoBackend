package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.Date;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IPlanningDao extends IGenericDao<Planning, Long>{
    
    public Planning getByEnqueteur(Enqueteur enqueteur) throws DataAccessException; 
    
    public Planning getByEnqueteurDate(Date date , Enqueteur enqueteur ) throws DataAccessException;
            
    
}
