package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IPlanningDao;
import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.Planning_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class PlanningDaoImpl extends GenericDao<Planning, Long> implements IPlanningDao{

    @Override
    public Planning getByEnqueteur(Enqueteur enqueteur) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Planning> cq = cb.createQuery(Planning.class);
        Root<Planning> planRoot = cq.from(Planning.class);
        cq.where(cb.equal(planRoot.get(Planning_.enqueteur), enqueteur));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
