package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IActionDao;
import com.royken.bracongo.survey.entities.Action;
import com.royken.bracongo.survey.service.IActionService;
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
public class ActionServiceImpl implements IActionService {

    @Inject
    private IActionDao actionDao;

    public IActionDao getActionDao() {
        return actionDao;
    }

    public void setActionDao(IActionDao actionDao) {
        this.actionDao = actionDao;
    }

    @Override
    public Action saveOrUpdateAction(Action action) throws ServiceException {
        try {
            if (action.getId() == null) {
                return actionDao.create(action);
            } else {
                return actionDao.update(action);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ActionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Action findActionById(Long id) throws ServiceException {
        try {
            return actionDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ActionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }return null;
        
    }

    @Override
    public void deleteAction(Long id) throws ServiceException {
        try {
            Action action = actionDao.findById(id);
            if(action != null){
                actionDao.delete(action);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ActionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Action> findAllAction() throws ServiceException {
        try {
            return actionDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ActionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Action> getByDates(Date debut, Date fin) throws ServiceException {
        try {
            return actionDao.getAllActionByDates(debut, fin);
        } catch (DataAccessException ex) {
            Logger.getLogger(ActionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
}
