package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Circuit;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface ICircuitService {
    /**
     * Enregistre ou modifie un circuit dans la BD
     * @param circuit le circuit
     * @return le circuit
     * @throws ServiceException 
     */
    public Circuit saveOrUpdateCircuit(Circuit circuit) throws ServiceException;
    
    /**
     * Retourne un circuit connaissant son ID
     * @param id l'id
     * @return le circuit
     * @throws ServiceException 
     */
    public Circuit findCircuitById(Long id) throws ServiceException;
    
    /**
     * Supprime un circuit dans la BD connaissant son ID
     * @param id l'ID du circuit
     * @throws ServiceException 
     */
    public void deleteCircuit(Long id) throws ServiceException;
    
    /**
     * Retourne la liste des circuits de la BD
     * @return la liste
     * @throws ServiceException 
     */
    public List<Circuit> findAllCircuit() throws ServiceException;
    
}
