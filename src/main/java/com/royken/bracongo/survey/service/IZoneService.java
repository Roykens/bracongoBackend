package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Zone;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IZoneService {
    
    public Zone saveOrUpdateZone(Zone zone) throws ServiceException;
    
    public Zone findZoneById(Long id) throws ServiceException;
    
    public void deleteZone(Long id) throws ServiceException;
    
    public List<Zone> findAllZone() throws ServiceException;
}
