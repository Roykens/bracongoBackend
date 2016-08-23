package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Commentaire;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface ICommentaireService {
    
    /**
     * Retourne la liste des commentaires envoyées sur une plage de dates
     * @param debut le début de la date
     * @param fin la fin de la date
     * @return la liste
     * @throws ServiceException 
     */
    public List<Commentaire> getAllByDates(Date debut, Date fin) throws ServiceException;
}
