package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IsecteurDao;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.Secteur_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class SecteurDaoImpl extends GenericDao<Secteur, Long> implements IsecteurDao{

    @Override
    public Secteur findByCode(String code) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Secteur> cq = cb.createQuery(Secteur.class);
        Root<Secteur> sectRoot = cq.from(Secteur.class);
        cq.where(cb.like(sectRoot.get(Secteur_.code), code));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
