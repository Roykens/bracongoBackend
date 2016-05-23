package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IFormatDao;
import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.Boisson_;
import com.royken.bracongo.survey.entities.Format;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.FormatBoisson_;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class FormatDaoImpl extends GenericDao<Format, Long> implements IFormatDao{

    @Override
    public List<Format> getAllFormatByTypeEnterprise(Boolean bracongo, TypeBoisson typeBoisson) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Format> cq = cb.createQuery(Format.class);
        Root<FormatBoisson> formBoiRoot = cq.from(FormatBoisson.class);
        Join<FormatBoisson, Format> toto = formBoiRoot.join(FormatBoisson_.format);
        Path<Boisson> boisPath = formBoiRoot.get(FormatBoisson_.boisson);
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if(bracongo != null){
            predicates.add(cb.equal(boisPath.get(Boisson_.isBracongo), bracongo));
        }
        
        if(typeBoisson != null){
            predicates.add(cb.equal(boisPath.get(Boisson_.typeBoisson), typeBoisson));
        }
        cq.select(toto);
        
         if (predicates.size() > 0) {
            cq.where((predicates.size() == 1) ? predicates.get(0) : cb.and(predicates.toArray(new Predicate[0])));
        }
        return getManager().createQuery(cq).getResultList();
    }
    
}
