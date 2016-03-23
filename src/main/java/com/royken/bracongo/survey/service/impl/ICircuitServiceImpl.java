package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.ICircuitDao;
import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.service.ICircuitService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.util.Collections;
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
public class ICircuitServiceImpl implements ICircuitService{
    
    @Inject
    private ICircuitDao circuitDao;

    public ICircuitDao getCircuitDao() {
        return circuitDao;
    }

    public void setCircuitDao(ICircuitDao circuitDao) {
        this.circuitDao = circuitDao;
    }
    
    @Override
    public Circuit saveOrUpdateCircuit(Circuit circuit) throws ServiceException {
        try {
            if(circuit.getId() == null){           
                return circuitDao.create(circuit);
                }
            else{
                return circuitDao.update(circuit);
            }
            } catch (DataAccessException ex) {
                Logger.getLogger(ICircuitServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }

    @Override
    public Circuit findCircuitById(Long id) throws ServiceException {
        try {
            return circuitDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ICircuitServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteCircuit(Long id) throws ServiceException {
        try {
            Circuit circuit = circuitDao.findById(id);
            if(circuit != null){
                circuitDao.delete(circuit);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ICircuitServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Circuit> findAllCircuit() throws ServiceException {
        try {
            return circuitDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ICircuitServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  Collections.EMPTY_LIST;
    }
    
}
