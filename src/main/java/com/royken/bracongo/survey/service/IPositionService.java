package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Position;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IPositionService {
    
    public Position saveOrUpdatePosition(Position position) throws ServiceException;
    
    public Position findPositionById(Long id) throws ServiceException;
    
    public void deletePosition(Long id) throws ServiceException;
    
    public List<Position> findAllPosition() throws ServiceException;
}
