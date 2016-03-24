package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Reponse;
import com.royken.generic.dao.DataAccessException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */

@Local
public interface IReponseService {
    
    public Reponse saveOrUpdateReponse(Reponse reponse) throws ServiceException;
    
    public Reponse findReponseById(Reponse reponse) throws ServiceException;
    
    public List<Reponse> findAllReponse() throws ServiceException;
    
    public List<Reponse> findAllReponseBetweenDates(Date debut, Date fin) throws DataAccessException;
    
    public List<Reponse> findAllReponseByDate(Date date) throws ServiceException;
    
    public void deleteReponse(Long id) throws ServiceException;
}
