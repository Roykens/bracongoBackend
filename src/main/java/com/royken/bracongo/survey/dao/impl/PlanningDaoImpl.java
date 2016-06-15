package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IPlanningDao;
import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.Planning_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.Date;
import java.util.List;
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
        cq.where(cb.and(cb.equal(planRoot.get(Planning_.enqueteur), enqueteur), cb.equal(planRoot.get(Planning_.active), 1)));
        cq.select(planRoot);
        cq.orderBy(cb.desc(planRoot.get(Planning_.datePlaning)));
        return getManager().createQuery(cq).getResultList().get(0);
    }

    @Override
    public List<Planning> findAll() throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Planning> cq = cb.createQuery(Planning.class);
        Root<Planning> pRoot = cq.from(Planning.class);
        cq.where(cb.equal(pRoot.get(Planning_.active), 1));
        cq.orderBy(cb.desc(pRoot.get(Planning_.datePlaning)));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public Planning getByEnqueteurDate(Date date, Enqueteur enqueteur) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Planning> cq = cb.createQuery(Planning.class);
        Root<Planning> plRoot = cq.from(Planning.class);
        cq.where(cb.and(cb.equal(plRoot.get(Planning_.datePlaning), date), cb.equal(plRoot.get(Planning_.enqueteur), enqueteur)));
        return getManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<Planning> findAllActive() throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Planning> cq = cb.createQuery(Planning.class);
        Root<Planning> plRoot = cq.from(Planning.class);
        cq.where(cb.equal(plRoot.get(Planning_.active), 1));
         cq.orderBy(cb.desc(plRoot.get(Planning_.datePlaning)));
        return getManager().createQuery(cq).getResultList();
    }
    
    
    
}
