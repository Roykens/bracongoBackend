package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Action;
import com.royken.bracongo.survey.entities.Circuit;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface ICircuitService {
    public Circuit saveOrUpdateCircuit(Circuit circuit) throws ServiceException;
    
    public Circuit findCircuitById(Long id) throws ServiceException;
    
    public void deleteCircuit(Long id) throws ServiceException;
    
    public List<Circuit> findAllCircuit() throws ServiceException;
    
}
