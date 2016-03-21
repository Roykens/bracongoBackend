package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.Question;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IReponseDao extends IGenericDao<Reponse, Long>{
    
    public List<PointDeVente> numberOfPDVByQuestionAnswer(Question question, boolean reponse, Date debut, Date fin) throws DataAccessException;
    
   
    
}
