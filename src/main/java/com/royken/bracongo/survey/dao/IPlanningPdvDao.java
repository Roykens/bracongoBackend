package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PlanningPdv;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IPlanningPdvDao extends IGenericDao<PlanningPdv, Long>{
    
    /**
     * Retourne la liste des planningPdv d'un planning (la liste des PDV)
     * @param planning le planning
     * @return la liste
     * @throws DataAccessException 
     */
    public List<PlanningPdv> findByPlanning(Planning planning) throws DataAccessException;
    
    /**
     * Retourne le planningPdv connaissant le planning et le PDV
     * @param planning le planning
     * @param pointDeVente le PDV
     * @return le PlanningPdv
     * @throws DataAccessException 
     */
    public PlanningPdv findByPlanningPdv(Planning planning, PointDeVente pointDeVente) throws DataAccessException;
}
