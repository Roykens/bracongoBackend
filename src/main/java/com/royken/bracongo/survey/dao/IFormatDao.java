package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Format;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IFormatDao extends IGenericDao<Format, Long>{
    
    public List<Format> getAllFormatByTypeEnterprise(Boolean bracongo, TypeBoisson typeBoisson) throws DataAccessException;
}
