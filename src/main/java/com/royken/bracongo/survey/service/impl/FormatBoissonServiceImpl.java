package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IFormatBoissonDao;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.service.IFormatBoissonService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
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
public class FormatBoissonServiceImpl implements IFormatBoissonService{

    @Inject
    private IFormatBoissonDao formatBoissonDao;

    public IFormatBoissonDao getFormatBoissonDao() {
        return formatBoissonDao;
    }

    public void setFormatBoissonDao(IFormatBoissonDao formatBoissonDao) {
        this.formatBoissonDao = formatBoissonDao;
    }
    
    @Override
    public FormatBoisson saveOrUpdateFormatBoisson(FormatBoisson boisson) throws ServiceException {
       try {
           if(boisson.getId() == null){           
                return formatBoissonDao.create(boisson);
                }
           else{
               return formatBoissonDao.update(boisson);
           }
            } catch (DataAccessException ex) {
                Logger.getLogger(FormatBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }

    @Override
    public FormatBoisson findFormatBoissonByIf(Long id) throws ServiceException {
        try {
            return formatBoissonDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(FormatBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteFormatBoisson(Long id) throws ServiceException {
        try {
            FormatBoisson formatBoisson = formatBoissonDao.findById(id);
            if(formatBoisson != null){
                formatBoissonDao.delete(formatBoisson);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(FormatBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<FormatBoisson> findAllFormatBoisson() throws ServiceException {
        try {
            return formatBoissonDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(FormatBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
