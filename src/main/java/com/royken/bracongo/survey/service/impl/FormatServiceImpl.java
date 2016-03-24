package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IFormatDao;
import com.royken.bracongo.survey.entities.Format;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.service.IFormatService;
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
public class FormatServiceImpl implements IFormatService{

    @Inject
    private IFormatDao formatDao;

    public IFormatDao getFormatDao() {
        return formatDao;
    }

    public void setFormatDao(IFormatDao formatDao) {
        this.formatDao = formatDao;
    }
    
    @Override
    public Format saveOrUpdateFormat(Format format) throws ServiceException {
        try {
            if(format.getId() == null){
                return formatDao.create(format);
            }
            else{
                return formatDao.update(format);
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Format findFormatById(Long id) throws ServiceException {
        try {
            return formatDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(FormatServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Format> findAllFormat() throws ServiceException {
        try {
            return formatDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(FormatServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void deleteFormat(Long id) throws ServiceException {
        try {
            Format format = formatDao.findById(id);
            if(format != null) {
                formatDao.delete(format);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(FormatServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    
}
