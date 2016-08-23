package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Circuit;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface ICircuitDao extends IGenericDao<Circuit, Long>{
    
    /**
     * La liste des circuits
     * @return la liste
     * @throws DataAccessException 
     */
    public List<Circuit> findAllActive() throws DataAccessException;
}
