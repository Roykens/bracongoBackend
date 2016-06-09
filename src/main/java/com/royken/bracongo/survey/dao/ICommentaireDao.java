package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Commentaire;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface ICommentaireDao extends IGenericDao<Commentaire, Long>{
    
    public List<Commentaire> getAllByDates(Date debut, Date fin) throws DataAccessException;
}
