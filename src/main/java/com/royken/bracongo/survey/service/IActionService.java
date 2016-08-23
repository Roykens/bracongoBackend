package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Action;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IActionService {
    
    /**
     * Enregistrer une action dans la BD
     * @param action L'actio
     * @return l'action
     * @throws ServiceException 
     */
    public Action saveOrUpdateAction(Action action) throws ServiceException;
    
    /**
     * Rechercher l'action connaissant l'ID
     * @param id l'Id
     * @return l'action
     * @throws ServiceException 
     */
    public Action findActionById(Long id) throws ServiceException;
    
    /**
     * Supprimer une action connaissant son ID
     * @param id l'Id
     * @throws ServiceException 
     */
    public void deleteAction(Long id) throws ServiceException;
    
    /**
     * Retourne la liste des actions de la BD
     * @return La liste
     * @throws ServiceException 
     */
    public List<Action> findAllAction() throws ServiceException;
    
    /**
     * Retourne la liste des actions entre un intervalle
     * @param debut le d"but de l'intervalle
     * @param fin la fin de l'intervalle
     * @return la liste
     * @throws ServiceException 
     */
    public List<Action> getByDates(Date debut, Date fin) throws ServiceException;
}
