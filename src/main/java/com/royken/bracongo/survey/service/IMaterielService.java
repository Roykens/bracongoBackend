package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Materiel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IMaterielService {
    
    public Materiel saveOrUpdateMateriel(Materiel materiel) throws ServiceException;
    
    public Materiel findMaterielById(Long id) throws ServiceException;
    
    public void deleteMateriel(Long id) throws ServiceException;
    
    public List<Materiel> findAllMateriel() throws ServiceException;
 }
