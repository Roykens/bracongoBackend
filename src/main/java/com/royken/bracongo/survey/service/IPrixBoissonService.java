package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.PrixBoisson;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IPrixBoissonService {
    
    public PrixBoisson saveOrUpdatePrixBoisson(PrixBoisson prixBoisson) throws ServiceException;
    
    public PrixBoisson findPrixBoissonById(Long id) throws ServiceException;
    
    public void deletePrixBoisson(Long id) throws ServiceException;
    
    public List<PrixBoisson> findAllPrixBoisson() throws ServiceException;
}
