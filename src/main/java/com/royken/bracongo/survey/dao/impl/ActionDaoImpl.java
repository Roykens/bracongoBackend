package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IActionDao;
import com.royken.bracongo.survey.entities.Action;
import com.royken.bracongo.survey.entities.Action_;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.bracongo.survey.entities.Reponse_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class ActionDaoImpl extends GenericDao<Action, Long> implements IActionDao{

    @Override
    public List<Action> getAllActionByDates(Date debut, Date fin) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Action> cq = cb.createQuery(Action.class);
        Root<Action> actRoot = cq.from(Action.class);
        Path<Reponse> rpsPath = actRoot.get(Action_.reponse);
        if(debut != null && fin != null){
           cq.where(cb.between(rpsPath.get(Reponse_.heureDeVisite), debut, fin));
        }        
        return getManager().createQuery(cq).getResultList();
    }   
}
