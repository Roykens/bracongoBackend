package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.projection.NomBoisson;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IFormatBoissonService {
    
    public FormatBoisson saveOrUpdateFormatBoisson(FormatBoisson boisson) throws ServiceException;
    
    public FormatBoisson findFormatBoissonByIf(Long id) throws ServiceException;
    
    public void deleteFormatBoisson(Long id) throws ServiceException;
    
    public List<FormatBoisson> findAllByEnterprise(boolean bracongo, TypeBoisson typeBoisson) throws ServiceException;
    
    public List<FormatBoisson> findAllFormatBoisson() throws ServiceException;
    
    public List<FormatBoisson> findByBoisson(Long idBoisson) throws ServiceException;
    
    
    public void deleteFormatBoisson(Long idBoisson, Long idFormat) throws ServiceException;
    
    public List<NomBoisson> getAllbyEnterprise(boolean isBracongo, TypeBoisson typeBoisson) throws ServiceException;
}
