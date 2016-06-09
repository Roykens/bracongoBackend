package com.royken.bracongo.survey.dao.impl;

import com.royken.bracongo.survey.dao.ICommentaireDao;
import com.royken.bracongo.survey.entities.Commentaire;
import com.royken.bracongo.survey.entities.Commentaire_;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.bracongo.survey.entities.Reponse_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class CommentaireDaoImpl extends GenericDao<Commentaire, Long> implements ICommentaireDao{

    @Override
    public List<Commentaire> getAllByDates(Date debut, Date fin) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Commentaire> cq = cb.createQuery(Commentaire.class);
        Root<Commentaire> comtRoot = cq.from(Commentaire.class);
        Path<Reponse> rpsPath = comtRoot.get(Commentaire_.reponse);
        if(debut != null && fin != null){
            cq.where(cb.between(rpsPath.get(Reponse_.heureDeVisite), debut, fin));
        }
        
        return getManager().createQuery(cq).getResultList();
    }
    
}
