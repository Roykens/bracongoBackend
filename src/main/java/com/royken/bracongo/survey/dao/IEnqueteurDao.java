package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IEnqueteurDao extends IGenericDao<Enqueteur, Long>{
    
    public Enqueteur findEnqueteurByMatricule(String matricule) throws DataAccessException;
    
    public Enqueteur findEnqueteurByUsernameAndPassword(String username, String password) throws DataAccessException;
}
