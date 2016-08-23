package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Format;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IFormatService {
    
    public Format saveOrUpdateFormat(Format format) throws ServiceException;
    
    public Format findFormatById(Long id) throws ServiceException;
    
    public List<Format> findAllFormat() throws ServiceException;
    
    public void deleteFormat(Long id) throws ServiceException;
}
