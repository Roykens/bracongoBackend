package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IBoissonDao extends IGenericDao<Boisson, Long>{
    
    public List<Boisson> findAllByEnterpriseAndType(boolean isBracongo, TypeBoisson typeBoisson) throws DataAccessException;
    
    public Boisson findByName(String name) throws DataAccessException;
    
}
