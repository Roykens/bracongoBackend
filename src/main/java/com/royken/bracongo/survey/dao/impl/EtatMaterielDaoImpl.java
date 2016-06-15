package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IEtatMaterielDao;
import com.royken.bracongo.survey.entities.EtatMateriel;
import com.royken.bracongo.survey.entities.EtatMateriel_;
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
public class EtatMaterielDaoImpl extends GenericDao<EtatMateriel, Long> implements IEtatMaterielDao{

    @Override
    public List<EtatMateriel> findAllactive() throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<EtatMateriel> cq = cb.createQuery(EtatMateriel.class);
        Root<EtatMateriel> etatRoot = cq.from(EtatMateriel.class);
        cq.where(cb.equal(etatRoot.get(EtatMateriel_.active), 1));
        return getManager().createQuery(cq).getResultList();
                
    }
    
}
