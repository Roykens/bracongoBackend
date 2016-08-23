package com.royken.bracongo.survey.service;

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
     
    /**
     * Enregistre ou modifie un format de boisson dans la BD
     * @param boisson la boisson
     * @return la boisson
     * @throws ServiceException 
     */
    public FormatBoisson saveOrUpdateFormatBoisson(FormatBoisson boisson) throws ServiceException;
    
    /**
     * Retourne le format de boisson avaec l'id
     * @param id l'id
     * @return le format de boisson
     * @throws ServiceException 
     */
    public FormatBoisson findFormatBoissonByIf(Long id) throws ServiceException;
    
    public void deleteFormatBoisson(Long id) throws ServiceException;
    
    public List<FormatBoisson> findAllByEnterprise(boolean bracongo, TypeBoisson typeBoisson) throws ServiceException;
    
    public List<FormatBoisson> findAllFormatBoisson() throws ServiceException;
    
    public List<FormatBoisson> findByBoisson(Long idBoisson) throws ServiceException;
    
    
    public void deleteFormatBoisson(Long idBoisson, Long idFormat) throws ServiceException;
    
    public List<NomBoisson> getAllbyEnterprise(Boolean isBracongo, TypeBoisson typeBoisson) throws ServiceException;
}
