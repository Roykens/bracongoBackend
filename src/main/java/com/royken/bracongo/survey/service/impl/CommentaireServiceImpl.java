package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.ICommentaireDao;
import com.royken.bracongo.survey.entities.Commentaire;
import com.royken.bracongo.survey.service.ICommentaireService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class CommentaireServiceImpl implements ICommentaireService{

    @Inject
    private ICommentaireDao commentaireDao;

    public ICommentaireDao getCommentaireDao() {
        return commentaireDao;
    }

    public void setCommentaireDao(ICommentaireDao commentaireDao) {
        this.commentaireDao = commentaireDao;
    }
    
    
    @Override
    public List<Commentaire> getAllByDates(Date debut, Date fin) throws ServiceException {
        try {
            return commentaireDao.getAllByDates(debut, fin);
        } catch (DataAccessException ex) {
            Logger.getLogger(CommentaireServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
    
}
