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
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class FormatBoissonDaoImpl extends GenericDao<FormatBoisson, Long> implements IFormatBoissonDao {

    @Override
    public List<FormatBoisson> findAllByTypeForEnterprise(boolean bracongo, TypeBoisson typeBoisson) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<FormatBoisson> cq = cb.createQuery(FormatBoisson.class);
        Root<FormatBoisson> fbRoot = cq.from(FormatBoisson.class);
        Path<Boisson> boissonPath = fbRoot.get(FormatBoisson_.boisson);
        cq.where(cb.and(cb.equal(boissonPath.get(Boisson_.isBracongo), bracongo), cb.equal(boissonPath.get(Boisson_.typeBoisson), typeBoisson)));
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
        cq.where(cb.equal(fmRoot.get(FormatBoisson_.boisson), boisson));
        return getManager().createQuery(cq).getResultList();
    }

}