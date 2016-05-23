package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IReponseDao;
import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.BoissonInfos;
import com.royken.bracongo.survey.entities.BoissonInfos_;
import com.royken.bracongo.survey.entities.Boisson_;
import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.entities.Circuit_;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.FormatBoisson_;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.PointDeVente_;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.bracongo.survey.entities.ReponseValue;
import com.royken.bracongo.survey.entities.Reponse_;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.TypeCategorie;
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
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class ReponseDaoImpl extends GenericDao<Reponse, Long> implements IReponseDao {

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
    public int countReponseGlobalStat(Planning planning, Secteur secteur, TypeRegime typeRegime, TypeCategorie categorie, Date debut, Date fin) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Reponse> cq = cb.createQuery(Reponse.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        Path<PointDeVente> pdvPath = rpsRoot.get(Reponse_.pointDeVente);
        Path<Circuit> cirPath = pdvPath.get(PointDeVente_.circuit);
        Path<Zone> zonePath = cirPath.get(Circuit_.zone);
        Path<Secteur> secPath = zonePath.get(Zone_.secteur);
        //Path<DisponibiliteBoisson> dispPath = rpsRoot.get(Reponse_.disponibiliteBoisson);
        //Path<FormatBoisson> frmPat = dispPath.get(DisponibiliteBoisson_.formatBoisson);
        // Path<Boisson> bsPath = frmPat.get(FormatBoisson_.boisson);   
        if (debut != null) {
            if (fin != null) {
                predicates.add(cb.between(rpsRoot.get(Reponse_.heureDeVisite), debut, fin));
            }
        }

        if (planning != null) {
            predicates.add(cb.equal(rpsRoot.get(Reponse_.planning), planning));
        }

        if (secteur != null) {
            predicates.add(cb.equal(secPath, secteur));
        }
        if (typeRegime != null) {
            predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), typeRegime));
        }
        if (categorie != null) {
            predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeCategorie), categorie));
        }

        //  if(null == fin){
        //      predicates.add(cb.equal(rpsRoot.get(Reponse_.heureDeVisite), debut));
        // }
        cq.select(rpsRoot);
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList().size();
    }

/*    @Override
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
        if (fin != null) {
            predicates.add(cb.between(rpsRoot.get(Reponse_.heureDeVisite), debut, fin));
        }
        //   else{
        //     predicates.add(cb.equal(rpsRoot.get(Reponse_.heureDeVisite), debut));
        //}
        if (secteur != null) {
            predicates.add(cb.equal(secPath, secteur));
        }
        if (typeBoisson != null) {
            predicates.add(cb.equal(bsPath.get(Boisson_.typeBoisson), typeBoisson));
        }
        if (typeRegime != null) {
            predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), typeRegime));
        }
        //  if(null == fin){
        //      predicates.add(cb.equal(rpsRoot.get(Reponse_.heureDeVisite), debut));
        // }
        cq.select(rpsRoot);
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList().size();
        //return 0;
    }
*/
    @Override
    public Reponse findByPdvAndDate(PointDeVente pointDeVente, Date date) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Reponse> cq = cb.createQuery(Reponse.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        cq.where(cb.and(cb.equal(rpsRoot.get(Reponse_.pointDeVente), pointDeVente), cb.equal(rpsRoot.get(Reponse_.heureDeVisite), date)));
        return getManager().createQuery(cq).getSingleResult();
    }

    @Override
    public Reponse findByPlanningAndPdv(Planning planning, PointDeVente pointDeVente) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Reponse> cq = cb.createQuery(Reponse.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        cq.where(cb.and(cb.equal(rpsRoot.get(Reponse_.planning), planning), cb.equal(rpsRoot.get(Reponse_.pointDeVente), pointDeVente)));
        return getManager().createQuery(cq).getSingleResult();
    }

    @Override
    public int countDisponibiliteFormat(FormatBoisson formatBoisson, Boolean DiEtOr, Boolean pve, Date debut, Date fin, Boolean biere, Boolean bracongo) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Reponse> cq = cb.createQuery(Reponse.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        Path<PointDeVente> pdvPath = rpsRoot.get(Reponse_.pointDeVente);
        //Root<FormatBoisson> formBoisRoot = cq.from(FormatBoisson.class);
        Root<BoissonInfos> bInRoot = cq.from(BoissonInfos.class);
        Expression<List<BoissonInfos>> ab = rpsRoot.get(Reponse_.boissonInfoss);
        Join<Reponse, BoissonInfos> toto = rpsRoot.join(Reponse_.boissonInfoss);
        Path<FormatBoisson> formPat = toto.get(BoissonInfos_.formatBoisson);
        Path<Boisson> boisPath = formPat.get(FormatBoisson_.boisson);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (biere != null) {
            if (biere) {
                predicates.add(cb.equal(boisPath.get(Boisson_.typeBoisson), TypeBoisson.BI));
            } else {
                predicates.add(cb.equal(boisPath.get(Boisson_.typeBoisson), TypeBoisson.BG));
            }
        }
        if (bracongo != null) {
            predicates.add(cb.equal(boisPath.get(Boisson_.isBracongo), bracongo));
        }

        if (formatBoisson != null) {
            predicates.add(cb.equal(toto.get(BoissonInfos_.formatBoisson), formatBoisson));
        }
        predicates.add(cb.equal(toto.get(BoissonInfos_.disponibilite), true));
        if (debut != null && fin != null) {
            predicates.add(cb.between(rpsRoot.get(Reponse_.heureDeVisite), debut, fin));
        }
        if (DiEtOr != null) {
            if (DiEtOr) {
                predicates.add(cb.or(cb.equal(pdvPath.get(PointDeVente_.typeCategorie), TypeCategorie.Di), cb.equal(pdvPath.get(PointDeVente_.typeCategorie), TypeCategorie.Or)));
            } else {
                predicates.add(cb.or(cb.equal(pdvPath.get(PointDeVente_.typeCategorie), TypeCategorie.Ag), cb.equal(pdvPath.get(PointDeVente_.typeCategorie), TypeCategorie.Br)));
            }
        }
        if (pve != null) {
            if (pve) {
                predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), TypeRegime.PVE));
            } else {
                predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), TypeRegime.Mixte));
            }
        }
        cq.select(rpsRoot);
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList().size();
    }

    @Override
    public int dispoibiliteFormatReponse(FormatBoisson formatBoisson, Reponse reponse) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<BoissonInfos> cq = cb.createQuery(BoissonInfos.class);
        Root<BoissonInfos> biRoot = cq.from(BoissonInfos.class);
        cq.where(cb.and(cb.equal(biRoot.get(BoissonInfos_.reponse), reponse), cb.equal(biRoot.get(BoissonInfos_.formatBoisson), formatBoisson)));
        //cq.select(biRoot.get(BoissonInfos_.disponibilite));
       // System.out.println("Les données");
       // System.out.println("LE FORMAT : " + formatBoisson.getId());
       // System.out.println("LA REPONSE : " + reponse.getId());
       // List<BoissonInfos> boi = getManager().createQuery(cq).getResultList();
       // System.out.println("Ma liste");
       // System.out.println(boi);
        BoissonInfos bi = getManager().createQuery(cq).getResultList().get(0);
        return (bi.isDisponibilite() == true) ? 1 : 0;
    }

    @Override
    public List<Reponse> findAllByDateTypeRegime(Date debut, Date fin, Boolean DiEtOr, Boolean pve, Boolean biere, Boolean bracongo) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Reponse> cq = cb.createQuery(Reponse.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        Path<PointDeVente> pdvPath = rpsRoot.get(Reponse_.pointDeVente);
        Join<Reponse, BoissonInfos> toto = rpsRoot.join(Reponse_.boissonInfoss);
        Path<FormatBoisson> formatPath = toto.get(BoissonInfos_.formatBoisson);
        Path<Boisson> boissonPath = formatPath.get(FormatBoisson_.boisson);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (debut != null) {
            if (fin != null) {
                predicates.add(cb.between(rpsRoot.get(Reponse_.heureDeVisite), debut, fin));
            }
        }

        if (DiEtOr != null) {
            if (DiEtOr) {
                predicates.add(cb.or(cb.equal(pdvPath.get(PointDeVente_.typeCategorie), TypeCategorie.Di), cb.equal(pdvPath.get(PointDeVente_.typeCategorie), TypeCategorie.Or)));
            } else {
                predicates.add(cb.or(cb.equal(pdvPath.get(PointDeVente_.typeCategorie), TypeCategorie.Br), cb.equal(pdvPath.get(PointDeVente_.typeCategorie), TypeCategorie.Ag)));
            }
        }

        if (pve != null) {
            if (pve) {
                predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), TypeRegime.PVE));
            } else {
                predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), TypeRegime.Mixte));
            }
        }

        if (biere != null) {
            if (biere) {
                predicates.add(cb.equal(boissonPath.get(Boisson_.typeBoisson), TypeBoisson.BI));
            } else {
                predicates.add(cb.equal(boissonPath.get(Boisson_.typeBoisson), TypeBoisson.BG));
            }
        }

        if (bracongo != null) {
            predicates.add(cb.equal(boissonPath.get(Boisson_.isBracongo), bracongo));
        }

        cq.select(rpsRoot);
        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public int prixMoyenFormat(FormatBoisson formatBoisson, Date debut, Date fin, Boolean pve) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<BoissonInfos> boisInfoRoot = cq.from(BoissonInfos.class);
        Path<Reponse> rpsPath = boisInfoRoot.get(BoissonInfos_.reponse);
        Path<PointDeVente> pdvPath = rpsPath.get(Reponse_.pointDeVente);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (debut != null) {
            if (fin != null) {
                predicates.add(cb.between(rpsPath.get(Reponse_.heureDeVisite), debut, fin));
            }
        }
        predicates.add(cb.equal(boisInfoRoot.get(BoissonInfos_.formatBoisson), formatBoisson));

        if (pve != null) {
            predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), pve ? TypeRegime.PVE : TypeRegime.Mixte));
        }

        predicates.add(cb.equal(boisInfoRoot.get(BoissonInfos_.disponibilite), true));

        Expression<Integer> sum = cb.sum(boisInfoRoot.get(BoissonInfos_.prixPdv));

        Expression<Long> total = cb.count(boisInfoRoot);

        Expression<Number> div = cb.quot(sum, total);

        cq.select(div);

        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        try {
            return getManager().createQuery(cq).getSingleResult().intValue();
        } catch (Exception e) {
            return 0;
        }
        
    }

    @Override
    public int countParEmballage(Boolean bracongo, Boolean pve, Date debut, Date fin) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        Path<PointDeVente> pdvPath = rpsRoot.get(Reponse_.pointDeVente);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(debut != null && fin != null){
            predicates.add(cb.between(rpsRoot.get(Reponse_.heureDeVisite), debut, fin));
        }
        
        if(pve != null){
            predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), pve ? TypeRegime.PVE:TypeRegime.Mixte));
        }
        Expression<Integer> sum = null;
        
        if(bracongo != null){
            if(bracongo){
                sum = cb.sum(rpsRoot.get(Reponse_.parcEmballageBrac));
            }
            else{
                sum = cb.sum(rpsRoot.get(Reponse_.parcEmballageBral));
            }
        }
      //  Expression<Long> total = cb.count(rpsRoot);
        //Expression<Number> div = cb.quot(sum, total);
        cq.select(sum);

        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        try {
            return getManager().createQuery(cq).getSingleResult().intValue();
        } catch (Exception e) {
            return 0;
        }
         
    }

    @Override
    public double nombreJourMoyenEcoule(Boolean bracongo, Boolean pve, Date debut, Date fin) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        Path<PointDeVente> pdvPath = rpsRoot.get(Reponse_.pointDeVente);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(debut != null && fin != null){
            predicates.add(cb.between(rpsRoot.get(Reponse_.heureDeVisite), debut, fin));
        }
        
        if(pve != null){
            predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), pve ? TypeRegime.PVE:TypeRegime.Mixte));
        }
        Expression<Integer> sum = null;
        
        if(bracongo != null){
            if(bracongo){
                sum = cb.sum(rpsRoot.get(Reponse_.jourDepuisDernierPassageFVD));
            }
            else{
                sum = cb.sum(rpsRoot.get(Reponse_.jourDernierPassageFVDBralimba));
            }
        }
        Expression<Long> total = cb.count(rpsRoot);
        Expression<Number> div = cb.quot(sum, total);
        cq.select(div);

        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        try {
            return getManager().createQuery(cq).getSingleResult().doubleValue();
        } catch (Exception e) {
            return 0;
        }
         
    }

    @Override
    public List<Date> getHeuresPassageSrd(Boolean bracongo, Boolean pve, Date debut, Date fin) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Date> cq = cb.createQuery(Date.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        Path<PointDeVente> pdvPath = rpsRoot.get(Reponse_.pointDeVente);
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if(debut != null && fin != null){
            predicates.add(cb.between(rpsRoot.get(Reponse_.heureDeVisite), debut, fin));
        }
        
        if(pve != null){
            if(pve){
                predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), TypeRegime.PVE));
            }
            else{
                predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), TypeRegime.Mixte));
            }
        }
        
        if(bracongo != null){
            if(bracongo){
                predicates.add(cb.equal(rpsRoot.get(Reponse_.srdBracongo), ReponseValue.OUI));
                cq.select(rpsRoot.get(Reponse_.heurePassageSrdBracongo));
            }
            else{
                predicates.add(cb.equal(rpsRoot.get(Reponse_.srdBralimba), ReponseValue.OUI));
                cq.select(rpsRoot.get(Reponse_.heurePassageSrdBralimba));
            }
        }
        
         if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        
            return getManager().createQuery(cq).getResultList();
        
    }

    @Override
    public int nombrepdvVisiteParSrd(Boolean bracongo, Boolean pve, Date debut, Date fin) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = cb.createQuery(Number.class);
        Root<Reponse> rpsRoot = cq.from(Reponse.class);
        Path<PointDeVente> pdvPath = rpsRoot.get(Reponse_.pointDeVente);
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if(debut != null && fin != null){
            predicates.add(cb.between(rpsRoot.get(Reponse_.heureDeVisite), debut, fin));
        }
        
        if(pve != null){
            if(pve){
                predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), TypeRegime.PVE));
            }
            else{
                predicates.add(cb.equal(pdvPath.get(PointDeVente_.typeRegime), TypeRegime.Mixte));
            }
        }
        
        Expression<Integer> sum = null;
        
        if(bracongo != null){
            if(bracongo){
                predicates.add(cb.equal(rpsRoot.get(Reponse_.srdBracongo), ReponseValue.OUI));
            }
            else{
                predicates.add(cb.equal(rpsRoot.get(Reponse_.srdBralimba), ReponseValue.OUI));
            }
        }
        Expression<Long> total = cb.count(rpsRoot);
        //Expression<Number> div = cb.quot(sum, total);
        cq.select(total);

        if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        try {
            return getManager().createQuery(cq).getSingleResult().intValue();
        } catch (Exception e) {
            return 0;
        }
    }

}
