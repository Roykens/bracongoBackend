package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.EtatPlv;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IEtatPlvService {
    
    public EtatPlv saveOrUpdateEtatPlv(EtatPlv etatPlv) throws ServiceException;
    
    public EtatPlv findEtatPlvById(Long id) throws ServiceException;
    
    public List<EtatPlv> findAllEtatPlv() throws ServiceException;
    
    public void deletetatPlv(Long id) throws ServiceException;
}
