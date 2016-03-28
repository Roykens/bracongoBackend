package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IBoissonDao;
import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.Boisson_;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class BoissonDaoImpl extends GenericDao<Boisson, Long> implements IBoissonDao{

    @Override
    public List<Boisson> findAllByEnterpriseAndType(boolean isBracongo, TypeBoisson typeBoisson) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Boisson> cq = cb.createQuery(Boisson.class);
        Root<Boisson> boissonRoot = cq.from(Boisson.class);
        cq.where(cb.and(cb.equal(boissonRoot.get(Boisson_.isBracongo), isBracongo), cb.equal(boissonRoot.get(Boisson_.typeBoisson), typeBoisson)));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public Boisson findByName(String name) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Boisson> cq= cb.createQuery(Boisson.class);
        Root<Boisson> bsRoot = cq.from(Boisson.class);
        cq.where(cb.like(bsRoot.get(Boisson_.nom), name));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}