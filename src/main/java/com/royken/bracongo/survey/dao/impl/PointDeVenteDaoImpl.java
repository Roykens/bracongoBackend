package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IPointDeVenteDao;
import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.entities.Circuit_;
import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.PointDeVente_;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.entities.Zone_;
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
public class PointDeVenteDaoImpl extends GenericDao<PointDeVente, Long> implements IPointDeVenteDao{
    
       @Override
    public List<PointDeVente> findByEnqueteur(Enqueteur enqueteur) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<PointDeVente> cq = cb.createQuery(PointDeVente.class);
        Root<PointDeVente> pdvRoot = cq.from(PointDeVente.class);
        Path<Circuit> cirPath = pdvRoot.get(PointDeVente_.circuit);
        Path<Zone> zonePath = cirPath.get(Circuit_.zone);
        cq.where(cb.equal(zonePath.get(Zone_.secteur), enqueteur.getSecteur()));
        return getManager().createQuery(cq).getResultList();
    }
}
