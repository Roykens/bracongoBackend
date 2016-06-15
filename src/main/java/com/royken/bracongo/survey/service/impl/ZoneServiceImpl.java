package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IZoneDao;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.service.IZoneService;
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
public class ZoneServiceImpl implements IZoneService {

    @Inject
    private IZoneDao zoneDao;

    public IZoneDao getZoneDao() {
        return zoneDao;
    }

    public void setZoneDao(IZoneDao zoneDao) {
        this.zoneDao = zoneDao;
    }

    @Override
    public Zone saveOrUpdateZone(Zone zone) throws ServiceException {
        try {
            if (zone.getId() == null) {
                zone.setActive(1);
                return zoneDao.create(zone);
            } else {
                return zoneDao.update(zone);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ZoneServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Zone findZoneById(Long id) throws ServiceException {
        try {
            return zoneDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ZoneServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteZone(Long id) throws ServiceException {
        try {
            Zone zone = zoneDao.findById(id);
            if(zone != null){
                zone.setActive(0);
                zoneDao.update(zone);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ZoneServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Zone> findAllZone() throws ServiceException {
        try {
            return zoneDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(ZoneServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
