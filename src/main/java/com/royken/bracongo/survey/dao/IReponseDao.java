package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Reponse;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IReponseDao extends IGenericDao<Reponse, Long>{
    
    
    public List<Reponse> findReponseBetweenDates(Date debut, Date fin) throws DataAccessException;
    
    public List<Reponse> findReponseForDate(Date date) throws DataAccessException;
}
