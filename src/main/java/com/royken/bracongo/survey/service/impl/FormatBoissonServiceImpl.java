package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IBoissonDao;
import com.royken.bracongo.survey.dao.IFormatBoissonDao;
import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.projection.NomBoisson;
import com.royken.bracongo.survey.service.IFormatBoissonService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.util.ArrayList;
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
public class FormatBoissonServiceImpl implements IFormatBoissonService{

    @Inject
    private IFormatBoissonDao formatBoissonDao;
    
    @Inject
    private IBoissonDao boissonDao;

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
    
     @Override
    public List<FormatBoisson> findAllByEnterprise(boolean bracongo, TypeBoisson typeBoisson) throws ServiceException {
        try {
            return formatBoissonDao.findAllByTypeForEnterprise(bracongo, typeBoisson);
        } catch (DataAccessException ex) {
            Logger.getLogger(FormatBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<FormatBoisson> findByBoisson(Long idBoisson) throws ServiceException {
        try {
            Boisson boisson = boissonDao.findById(idBoisson);
            if(boisson != null){
                return formatBoissonDao.findByBoisson(boisson);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(FormatBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    @Override
    public void deleteFormatBoisson(Long idBoisson, Long idFormat) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NomBoisson> getAllbyEnterprise(Boolean isBracongo, TypeBoisson typeBoisson) throws ServiceException {
        try {
            List<NomBoisson> nomBoissons = new ArrayList<NomBoisson>();
            List<FormatBoisson> boissons = formatBoissonDao.findAllByTypeForEnterprise(isBracongo, typeBoisson);
            for (FormatBoisson boisson : boissons) {
                NomBoisson nomBoisson = new NomBoisson();
                nomBoisson.setIdFormatBoisson(boisson.getId());
                nomBoisson.setBiere((boisson.getBoisson().getTypeBoisson() == TypeBoisson.BI));
                nomBoisson.setBracongo(boisson.getBoisson().isIsBracongo());
                nomBoisson.setPrix(boisson.getPrix());
                nomBoisson.setNomFormat(boisson.getBoisson().getNom() + " "+boisson.getFormat().getVolume() + " cl");
                nomBoissons.add(nomBoisson);
            }
            return nomBoissons;
        } catch (DataAccessException ex) {
            Logger.getLogger(FormatBoissonServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
    
}
