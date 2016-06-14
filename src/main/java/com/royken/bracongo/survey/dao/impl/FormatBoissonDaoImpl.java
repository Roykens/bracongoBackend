package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IFormatBoissonDao;
import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.Boisson_;
import com.royken.bracongo.survey.entities.Format;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.FormatBoisson_;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class FormatBoissonDaoImpl extends GenericDao<FormatBoisson, Long> implements IFormatBoissonDao {

    @Override
    public List<FormatBoisson> findAllByTypeForEnterprise(Boolean bracongo, TypeBoisson typeBoisson) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<FormatBoisson> cq = cb.createQuery(FormatBoisson.class);
        Root<FormatBoisson> fbRoot = cq.from(FormatBoisson.class);
        Path<Boisson> boissonPath = fbRoot.get(FormatBoisson_.boisson);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(bracongo != null){
            predicates.add(cb.equal(boissonPath.get(Boisson_.isBracongo), bracongo));
        }
        if(typeBoisson != null){
            predicates.add(cb.equal(boissonPath.get(Boisson_.typeBoisson), typeBoisson));
        }
        predicates.add(cb.equal(fbRoot.get(FormatBoisson_.active), 1));
        
         cq.select(fbRoot).orderBy(cb.asc(boissonPath.get(Boisson_.nom)));
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public FormatBoisson findByBoissonAndFormat(Boisson boisson, Format format) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<FormatBoisson> cq = cb.createQuery(FormatBoisson.class);
        Root<FormatBoisson> fmRoot = cq.from(FormatBoisson.class);
        cq.where(cb.and(cb.equal(fmRoot.get(FormatBoisson_.boisson), boisson), cb.equal(fmRoot.get(FormatBoisson_.format), format)));
        return getManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<FormatBoisson> findByBoisson(Boisson boisson) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<FormatBoisson> cq = cb.createQuery(FormatBoisson.class);
        Root<FormatBoisson> fmRoot = cq.from(FormatBoisson.class);
        cq.where(cb.and(cb.equal(fmRoot.get(FormatBoisson_.boisson), boisson), cb.equal(fmRoot.get(FormatBoisson_.active), 1)));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<FormatBoisson> findAllByTypeForEnterpriseAndFormat(Boolean bracongo, TypeBoisson typeBoisson, Format format) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<FormatBoisson> cq = cb.createQuery(FormatBoisson.class);
        Root<FormatBoisson> formRoot = cq.from(FormatBoisson.class);
        Path<Boisson> boisPath = formRoot.get(FormatBoisson_.boisson);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(bracongo != null){
                predicates.add(cb.equal(boisPath.get(Boisson_.isBracongo), bracongo));
        }
        
        if(typeBoisson != null){
            predicates.add(cb.equal(boisPath.get(Boisson_.typeBoisson), typeBoisson));
        }
        
        if(format != null){
            predicates.add(cb.equal(formRoot.get(FormatBoisson_.format), format));
        }
        
        cq.select(formRoot);
         if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<FormatBoisson> findAllActive() throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<FormatBoisson> cq = cb.createQuery(FormatBoisson.class);
        Root<FormatBoisson> formRoot = cq.from(FormatBoisson.class);
        cq.where(cb.equal(formRoot.get(FormatBoisson_.active), 1));
        return getManager().createQuery(cq).getResultList();
    }

}