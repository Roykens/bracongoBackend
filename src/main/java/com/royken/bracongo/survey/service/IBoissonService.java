package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.dao.IBoissonDao;
import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IBoissonService {
    
    
    public Boisson saveOrUpdateBoisson(Boisson boisson) throws ServiceException;
    
    public Boisson findBoissonById(Long id) throws ServiceException;
    
    public void deleteBoisson(Long id) throws ServiceException;
    
    public List<Boisson> findAllBoisson() throws ServiceException;
    
    public List<Boisson> findBoissonByEnterpriseAndType(boolean isBracongo, TypeBoisson typeBoisson) throws ServiceException;
}
