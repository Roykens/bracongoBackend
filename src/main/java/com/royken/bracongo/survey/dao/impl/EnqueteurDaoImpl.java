package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IEnqueteurDao;
import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.Enqueteur_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class EnqueteurDaoImpl extends GenericDao<Enqueteur, Long> implements IEnqueteurDao{

    @Override
    public Enqueteur findEnqueteurByMatricule(String matricule) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Enqueteur> cq = cb.createQuery(Enqueteur.class);
        Root<Enqueteur> enqRoot = cq.from(Enqueteur.class);
        cq.where(cb.like(enqRoot.get(Enqueteur_.matricule), matricule));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
