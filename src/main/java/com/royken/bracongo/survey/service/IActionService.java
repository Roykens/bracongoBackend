package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Action;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IActionService {
    
    public Action saveOrUpdateAction(Action action) throws ServiceException;
    
    public Action findActionById(Long id) throws ServiceException;
    
    public void deleteAction(Long id) throws ServiceException;
    
    public List<Action> findAllAction() throws ServiceException;
}
