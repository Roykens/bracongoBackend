package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IPointDeVenteDao;
import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.entities.Circuit_;
import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.PointDeVente_;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.entities.Zone_;
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
public class PointDeVenteDaoImpl extends GenericDao<PointDeVente, Long> implements IPointDeVenteDao{
    
       @Override
    public List<PointDeVente> findByEnqueteur(Enqueteur enqueteur) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<PointDeVente> cq = cb.createQuery(PointDeVente.class);
        Root<PointDeVente> pdvRoot = cq.from(PointDeVente.class);
        Path<Circuit> cirPath = pdvRoot.get(PointDeVente_.circuit);
        Path<Zone> zonePath = cirPath.get(Circuit_.zone);
        cq.where(cb.equal(cb.equal(zonePath.get(Zone_.secteur), enqueteur.getSecteur()), cb.equal(pdvRoot.get(PointDeVente_.active), 1)));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<PointDeVente> findBySecteurZoneCircuit(Secteur secteur, Zone zone, Circuit circuit) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<PointDeVente> cq = cb.createQuery(PointDeVente.class);
        Root<PointDeVente> pdvRoot = cq.from(PointDeVente.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        Path<Circuit> circPath = pdvRoot.get(PointDeVente_.circuit);
        Path<Zone> zonePath = circPath.get(Circuit_.zone);
        Path<Secteur> sectPath = zonePath.get(Zone_.secteur);
        if(circuit != null){
            predicates.add(cb.equal(circPath, circuit));
        }
        if(zone != null){
            predicates.add(cb.equal(zonePath, zone));
        }
        
        if(secteur != null){
            predicates.add(cb.equal(sectPath, secteur));
        }
        predicates.add(cb.equal(pdvRoot.get(PointDeVente_.active), 1));
        
        cq.select(pdvRoot);
        
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<PointDeVente> findAllActive() throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<PointDeVente> cq = cb.createQuery(PointDeVente.class);
        Root<PointDeVente> pdvRoot = cq.from(PointDeVente.class);
        cq.where(cb.equal(pdvRoot.get(PointDeVente_.active), 1));
        return getManager().createQuery(cq).getResultList();
    }
}
