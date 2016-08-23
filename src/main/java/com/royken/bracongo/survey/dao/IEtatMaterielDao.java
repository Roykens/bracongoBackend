package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.EtatMateriel;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IEtatMaterielDao extends IGenericDao<EtatMateriel, Long>{
    
    /**
     * Retourne la liste des matériels
     * @return la liste
     * @throws DataAccessException 
     */
    public List<EtatMateriel> findAllactive() throws DataAccessException;
}
