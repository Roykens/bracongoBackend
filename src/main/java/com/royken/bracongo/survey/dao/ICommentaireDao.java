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
    
    /**
     * La liste des désidératats des tenanciers dans une plage de dates
     * @param debut le début de la plage
     * @param fin la fin de la plage
     * @return la liste des désidératats
     * @throws DataAccessException 
     */
    public List<Commentaire> getAllByDates(Date debut, Date fin) throws DataAccessException;
}
