package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IReponseDao;
import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.Boisson_;
import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.entities.Circuit_;
import com.royken.bracongo.survey.entities.DisponibiliteBoisson;
import com.royken.bracongo.survey.entities.DisponibiliteBoisson_;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.FormatBoisson_;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.PointDeVente_;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.bracongo.survey.entities.Reponse_;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.entities.Zone_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public int countReponseByCriteria(Secteur secteur, TypeBoisson typeBoisson, TypeRegime typeRegime, Date debut, Date fin) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Reponse> cq = cb.createQuery(Reponse.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        Path<PointDeVente> pdvPath = rpsRoot.get(Reponse_.pointDeVente);
        Path<Circuit> cirPath = pdvPath.get(PointDeVente_.circuit);
        Path<Zone> zonePath = cirPath.get(Circuit_.zone);
        Path<Secteur> secPath = zonePath.get(Zone_.secteur);
        Path<DisponibiliteBoisson> dispPath = rpsRoot.get(Reponse_.disponibiliteBoisson);
        Path<FormatBoisson> frmPat = dispPath.get(DisponibiliteBoisson_.formatBoisson);
        Path<Boisson> bsPath = frmPat.get(FormatBoisson_.boisson);       
        if(fin != null){
            predicates.add(cb.between(rpsRoot.get(Reponse_.heureDeVisite), debut, fin));
        }
        else{
            predicates.add(cb.equal(rpsRoot.get(Reponse_.heureDeVisite), debut));
        }
        if(secteur != null){
            predicates.add(cb.equal(secPath, secteur));
        }
        if(typeBoisson != null){
            predicates.add(cb.equal(bsPath.get(Boisson_.typeBoisson), typeBoisson));
        }
        if(typeRegime != null){
            predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), typeRegime));
        }
        if(null == fin){
            predicates.add(cb.equal(rpsRoot.get(Reponse_.heureDeVisite), debut));
        }
        cq.select(rpsRoot);
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList().size();
        //return 0;
    }

    @Override
    public Reponse findByPdvAndDate(PointDeVente pointDeVente, Date date) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Reponse> cq = cb.createQuery(Reponse.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        cq.where(cb.and(cb.equal(rpsRoot.get(Reponse_.pointDeVente), pointDeVente), cb.equal(rpsRoot.get(Reponse_.heureDeVisite), date)));
        return getManager().createQuery(cq).getSingleResult();
    }   
    
}
