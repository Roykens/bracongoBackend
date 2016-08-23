package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.IUtilisateurDao;
import com.royken.bracongo.survey.entities.Utilisateur;
import com.royken.bracongo.survey.entities.Utilisateur_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class UtilisateurDaoImpl extends GenericDao<Utilisateur, Long> implements IUtilisateurDao {

    @Override
    public Utilisateur findUtilisateurByLogin(String login, String password) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Utilisateur> cq = cb.createQuery(Utilisateur.class);
        Root<Utilisateur> utRoot = cq.from(Utilisateur.class);
        cq.where(cb.and(cb.like(utRoot.get(Utilisateur_.username), login), cb.like(utRoot.get(Utilisateur_.password), password)));
        try {
            return getManager().createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            Logger.getLogger(ReponseDaoImpl.class.getName()).log(Level.SEVERE, null, nre);
        }
        return null;
    }

}
