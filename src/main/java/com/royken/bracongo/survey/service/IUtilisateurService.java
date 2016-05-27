package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Utilisateur;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IUtilisateurService {
    
    public Utilisateur findByLoginAndPassword(String login, String password) throws ServiceException;
}
