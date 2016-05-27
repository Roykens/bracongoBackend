package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IUtilisateurDao;
import com.royken.bracongo.survey.entities.Utilisateur;
import com.royken.bracongo.survey.service.IUtilisateurService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class UtilisateurServiceImpl implements IUtilisateurService{
    
    @Inject
    private IUtilisateurDao utilisateurDao;

    public IUtilisateurDao getUtilisateurDao() {
        return utilisateurDao;
    }

    public void setUtilisateurDao(IUtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }
    
    @Override
    public Utilisateur findByLoginAndPassword(String login, String password) throws ServiceException {
        try {
            return utilisateurDao.findUtilisateurByLogin(login, password);
        } catch (DataAccessException ex) {
            Logger.getLogger(UtilisateurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
