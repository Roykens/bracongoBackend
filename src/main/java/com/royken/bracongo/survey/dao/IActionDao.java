package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Action;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IActionDao extends IGenericDao<Action, Long>{
 
    /**
     * La liste des actions des enquêtes dur une période
     * @param debut le début de la période
     * @param fin la fin de la période
     * @return La liste des actions à mener sur différents PDV
     * @throws DataAccessException 
     */
    public List<Action> getAllActionByDates(Date debut, Date fin) throws DataAccessException;
}
