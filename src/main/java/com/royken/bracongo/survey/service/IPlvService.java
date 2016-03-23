package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.PLV;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IPlvService{
    public PLV saveOrUpdatePlv(PLV plv) throws ServiceException;
    
    public PLV findByPlvId(Long id) throws ServiceException;
    
    public void deletePlv(Long id) throws ServiceException;
    
    public List<PLV> findAllPlv() throws ServiceException;
    
}
