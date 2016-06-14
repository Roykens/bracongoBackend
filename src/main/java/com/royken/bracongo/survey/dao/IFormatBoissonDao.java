package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.Format;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IFormatBoissonDao extends IGenericDao<FormatBoisson, Long>{
    
    public List<FormatBoisson> findAllActive() throws DataAccessException;
    
    public List<FormatBoisson> findAllByTypeForEnterprise(Boolean bracongo, TypeBoisson typeBoisson)throws DataAccessException;
    
    public FormatBoisson findByBoissonAndFormat(Boisson boisson, Format format) throws DataAccessException;
    
    public List<FormatBoisson> findByBoisson(Boisson boisson) throws DataAccessException;
    
    public List<FormatBoisson> findAllByTypeForEnterpriseAndFormat(Boolean bracongo, TypeBoisson typeBoisson, Format format) throws DataAccessException;
    
}
