package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Secteur;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IsecteurDao extends IGenericDao<Secteur, Long>{
    /**
     * Retourne le secteur ayant un code donnée
     * @param code le code
     * @return le secteur
     * @throws DataAccessException 
     */
    public Secteur findByCode(String code) throws DataAccessException;
}
