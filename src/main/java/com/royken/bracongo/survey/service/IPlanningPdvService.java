package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PlanningPdv;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IPlanningPdvService {
    
    public PlanningPdv saveOrUpdatePlanningPdv(PlanningPdv planningPdv) throws ServiceException;
    
    public void deletePlanningPdv(Long idPpdv) throws ServiceException;
    
    public List<PlanningPdv> findByPlanning(Long idPlanning) throws ServiceException;
    
    public PlanningPdv save(Long idPlanning, Long idPdv) throws ServiceException;
}
