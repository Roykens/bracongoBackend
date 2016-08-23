package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Format;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IFormatDao extends IGenericDao<Format, Long>{
    
    /**
     * Rtourne la liste des formats d'un type de boisson d'une entreprise
     * @param bracongo l'entreprise (True pour Bracongo)
     * @param typeBoisson le type de boisson (BI pour bière, BG pour boisson gazeuse)
     * @return
     * @throws DataAccessException 
     */
    public List<Format> getAllFormatByTypeEnterprise(Boolean bracongo, TypeBoisson typeBoisson) throws DataAccessException;
}
