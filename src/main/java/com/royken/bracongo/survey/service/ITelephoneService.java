package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Action;
import com.royken.bracongo.survey.entities.Telephone;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface ITelephoneService {
    
    public Telephone saveOrUpdateTelephone(Telephone telephone) throws ServiceException;
    
    public Telephone findTelephoneById(Long id) throws ServiceException;
    
    public void deleteTelephone(Long id) throws ServiceException;
    
    public List<Telephone> findAllTelephone() throws ServiceException;
    
}
