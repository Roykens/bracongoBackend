package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IReponseDao;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.bracongo.survey.entities.Reponse_;
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
public class ReponseDaoImpl extends GenericDao<Reponse, Long> implements IReponseDao{

    @Override
    public List<Reponse> findReponseBetweenDates(Date debut, Date fin) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Reponse> cq = cb.createQuery(Reponse.class);
        Root<Reponse> repRoot = cq.from(Reponse.class);
        cq.where(cb.between(repRoot.get(Reponse_.heureDeVisite), debut, fin));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Reponse> findReponseForDate(Date date) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Reponse> cq = cb.createQuery(Reponse.class);
        Root<Reponse> repRoot = cq.from(Reponse.class);
        cq.where(cb.and(cb.equal(repRoot.get(Reponse_.heureDeVisite), date)));
        return getManager().createQuery(cq).getResultList();
    }
    
}
