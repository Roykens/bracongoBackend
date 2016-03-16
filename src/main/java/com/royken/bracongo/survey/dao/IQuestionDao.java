package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Question;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IQuestionDao extends IGenericDao<Question, Long>{
    
    public Question findQuestionByIntitule(String intitule) throws DataAccessException;
    
}
