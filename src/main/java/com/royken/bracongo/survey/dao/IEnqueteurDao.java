package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IEnqueteurDao extends IGenericDao<Enqueteur, Long>{
    
    /**
     * l'enqueteur ayant un matricule donné
     * @param matricule le matricule
     * @deprecated 
     * @return l'enqueteur
     * @throws DataAccessException 
     */
    @Deprecated
    public Enqueteur findEnqueteurByMatricule(String matricule) throws DataAccessException;
    
    /**
     * Retourne l'enquteur ayant un username et un mot de passe donné
     * @param username le username
     * @param password le mot de passe
     * @return l'enqueteur
     * @throws DataAccessException 
     */
    public Enqueteur findEnqueteurByUsernameAndPassword(String username, String password) throws DataAccessException;
}
