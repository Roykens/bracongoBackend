package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IPositionDao;
import com.royken.bracongo.survey.entities.Position;
import com.royken.bracongo.survey.service.IPositionService;
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
public class PositionServiceImpl implements IPositionService {

    @Inject
    private IPositionDao positionDao;

    @Override
    public Position saveOrUpdatePosition(Position position) throws ServiceException {
        try {
            if (position.getId() == null) {
                return positionDao.create(position);
            }
            else{
                return positionDao.update(position);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PositionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Position findPositionById(Long id) throws ServiceException {
        try {
            return positionDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(PositionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deletePosition(Long id) throws ServiceException {
        try {
            Position position = positionDao.findById(id);
            if(position != null){
                positionDao.delete(position);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PositionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Position> findAllPosition() throws ServiceException {
        try {
            return positionDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(PositionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
