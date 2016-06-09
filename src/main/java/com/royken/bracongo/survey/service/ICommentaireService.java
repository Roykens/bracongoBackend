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
    
    public List<Commentaire> getAllByDates(Date debut, Date fin) throws ServiceException;
}
