package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.ICircuitDao;
import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.entities.Circuit_;
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
public class CircuitDaoImpl extends GenericDao<Circuit, Long> implements ICircuitDao{

    @Override
    public List<Circuit> findAllActive() throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Circuit> cq = cb.createQuery(Circuit.class);
        Root<Circuit> circRoot = cq.from(Circuit.class);
        cq.where(cb.equal(circRoot.get(Circuit_.active), 1));
        return getManager().createQuery(cq).getResultList();
    }
    
}
