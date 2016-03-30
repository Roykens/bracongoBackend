package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PlanningPdv;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IPlanningPdvDao extends IGenericDao<PlanningPdv, Long>{
    
    public List<PlanningPdv> findByPlanning(Planning planning) throws DataAccessException;
}
