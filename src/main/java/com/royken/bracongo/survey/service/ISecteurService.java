package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Action;
import com.royken.bracongo.survey.entities.Secteur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface ISecteurService {
    
     public Secteur saveOrUpdateSecteur(Secteur secteur) throws ServiceException;
    
    public Secteur findSecteurById(Long id) throws ServiceException;
    
    public void deleteSecteur(Long id) throws ServiceException;
    
    public List<Secteur> findAllSecteur() throws ServiceException;
}
