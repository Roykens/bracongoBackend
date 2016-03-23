package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.FormatBoisson;
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
    
    public List<FormatBoisson> findAllFormatBoisson() throws ServiceException;
}
