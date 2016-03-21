package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IReponseDao;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.Question;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.bracongo.survey.entities.Reponse_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class ReponseDaoImpl extends GenericDao<Reponse, Long> implements IReponseDao{

    @Override
    public List<PointDeVente> numberOfPDVByQuestionAnswer(Question question, boolean reponse, Date debut, Date fin) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<PointDeVente> cq = cb.createQuery(PointDeVente.class);
        CriteriaQuery<Reponse> cq2 = cb.createQuery(Reponse.class);
        Root<PointDeVente> pdvRoot = cq.from(PointDeVente.class);
        Root<Reponse> rpsRoot = cq2.from(Reponse.class);
      //  Path<Question> questionPath = ;
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        //predicates.add(cb.isMember(reponse, pdvRoot.get(PointDeVente_.reponses)));
        
        predicates.add(cb.equal(rpsRoot.get(Reponse_.question), question));
        if(debut != null ){
            predicates.add(cb.greaterThanOrEqualTo(rpsRoot.get(Reponse_.dateReponse), debut));
        }
        if(fin != null ){
        //    predicates.add(cb.greaterThanOrEqualTo(rpsRoot.get(Reponse_.dateReponse), debut));
            predicates.add(cb.lessThanOrEqualTo(rpsRoot.get(Reponse_.dateReponse), fin));
        }
        predicates.add(cb.equal(rpsRoot.get(Reponse_.valeur), reponse));
        List<Reponse> reponses = new ArrayList<Reponse>();
        if (predicates.size() > 0) {
            cq2.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        cq2.select(rpsRoot);
        reponses = getManager().createQuery(cq2).getResultList();
        return null;
    }
    
}
