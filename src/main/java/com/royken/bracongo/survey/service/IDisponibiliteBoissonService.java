package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.DisponibiliteBoisson;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IDisponibiliteBoissonService {
    
    public DisponibiliteBoisson saveOrUpdateDisponibiliteBoisson(DisponibiliteBoisson disponibiliteBoisson) throws ServiceException;
    
    public DisponibiliteBoisson findDisponibiliteById(Long id) throws ServiceException;
    
    public void deleteDisponibilite(Long id) throws ServiceException;
    
    public List<DisponibiliteBoisson> findAllDisponibilite() throws ServiceException;
}
