package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Enqueteur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IEnqueteurService {
    
    public Enqueteur saveOrUpdateEnqueteur(Enqueteur enqueteur) throws ServiceException;
    
    public Enqueteur findEnqueteurById(Long id) throws ServiceException;
    
    public List<Enqueteur> findAllEnqueteur() throws ServiceException;
    
    public void deleteEnqueteur(Long id) throws ServiceException;
    
    public Enqueteur findByLoginAndPassord(String login, String password) throws ServiceException;
    
}
