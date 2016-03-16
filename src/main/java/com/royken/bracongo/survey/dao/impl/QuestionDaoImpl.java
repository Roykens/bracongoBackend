package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IQuestionDao;
import com.royken.bracongo.survey.entities.Question;
import com.royken.bracongo.survey.entities.Question_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.io.Serializable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class QuestionDaoImpl extends GenericDao<Question, Long> implements IQuestionDao{

    @Override
    public Question findQuestionByIntitule(String intitule) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Question> cq = cb.createQuery(Question.class);
        Root<Question> questRoot = cq.from(Question.class);
        cq.where(cb.like(questRoot.get(Question_.intitule), intitule));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
