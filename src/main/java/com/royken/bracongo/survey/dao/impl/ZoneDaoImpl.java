package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IZoneDao;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.entities.Zone_;
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
public class ZoneDaoImpl extends GenericDao<Zone, Long> implements IZoneDao{

    @Override
    public List<Zone> findAllActive() throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Zone> cq = cb.createQuery(Zone.class);
        Root<Zone> zoneRoot = cq.from(Zone.class);
        cq.where(cb.equal(zoneRoot.get(Zone_.active), 1));
        return getManager().createQuery(cq).getResultList();
    }
    
}
