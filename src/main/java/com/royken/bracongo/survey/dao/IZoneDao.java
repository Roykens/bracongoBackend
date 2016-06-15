package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Zone;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IZoneDao extends IGenericDao<Zone, Long>{
 
    public List<Zone> findAllActive()throws DataAccessException;
}
