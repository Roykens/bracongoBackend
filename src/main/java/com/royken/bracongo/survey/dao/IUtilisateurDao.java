package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Utilisateur;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IUtilisateurDao extends IGenericDao<Utilisateur, Long>{
 
    /**
     * Retourne l'utilisateur ayant un login et un mote de passe donné
     * @param login le login
     * @param password le mot de passe
     * @return l'uitlisateur
     * @throws DataAccessException 
     */
    public Utilisateur findUtilisateurByLogin(String login, String password) throws DataAccessException;
}
