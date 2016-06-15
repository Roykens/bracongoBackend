package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IPlanningPdvDao;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PlanningPdv;
import com.royken.bracongo.survey.entities.PlanningPdv_;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class PlanningPdvDaoImpl extends GenericDao<PlanningPdv, Long> implements IPlanningPdvDao{

    @Override
    public List<PlanningPdv> findByPlanning(Planning planning) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<PlanningPdv> cq = cb.createQuery(PlanningPdv.class);
        Root<PlanningPdv> pRoot = cq.from(PlanningPdv.class);
        //Path<PointDeVente> pdvPath = pRoot.get(PlanningPdv_.pointDeVente);
        cq.where(cb.and(cb.equal(pRoot.get(PlanningPdv_.planning), planning), cb.equal(pRoot.get(PlanningPdv_.active), 1))   );
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public PlanningPdv findByPlanningPdv(Planning planning, PointDeVente pointDeVente) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<PlanningPdv> cq = cb.createQuery(PlanningPdv.class);
        Root<PlanningPdv> planRoot = cq.from(PlanningPdv.class);
        cq.where(cb.and(cb.equal(planRoot.get(PlanningPdv_.planning), planning), cb.equal(planRoot.get(PlanningPdv_.pointDeVente), pointDeVente)));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}